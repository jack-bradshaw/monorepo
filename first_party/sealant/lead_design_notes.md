alright, im thinking about the api a little, i think theres room for improvement.

a few thoughts:

1. pipe implies SISO without modification in the middle but thats not really what it is. it can
trasnform and even make side-effect calls if necessary (it probablu shouldn't but it can). i guess
pipe isnt a terrible name though because it indicates SISO. 
2. inflow should be a pipe out not a hub for simplicity. makes it parallel with outflow (pipe to
out)
3. need to think about the core contract a little more

whats wrong with the core contract?

well, what exactly is connectible? implies that some arbitrary things are connected together but
does not imply what they are. 

yes, its a common interface for all things so say "are you connected to your predescessor"

and the predescessor is?

another connectible.

whwere is that self-documented by the api

whatre you thinking?

well right now the api is setup with factories. you make a hub then from the hub you make a pipe,
each is "connectible" the issue is, that means you can't construct a pipe independently of a hub. 

why is that an issue? 

it makes it hard to assemble independently constructed things togehter. you have to do

val pipe = hub.createPipe()
then hubFactory.createFrom(pipe) etc.

its like, you are forced to do a breadth first search basically, you couldn't assemble every piece
on a separate thread then integrate them. its an extreme example, but its important. moreover you 
couldn't supply some arbitrary pipe from some other object. thats  more realistic case

fun assembleFoo(hub, pipes, etc)

i see your point.

so each individual component must be individual constructible, then attachable.

how does this relate to connectible.

well it means the api should be more like:

interface Connectable {
  fun attachTo(connectable: Connectable)
}

alright.

i see how we got here. we began with flows which ARE somewhat haphazardly created . its rare to
assemble flow sections independencly and compose them, but its a useful task . you could do it
people just dont. if were going to thsi trouble though lets get it right.

so... we cannot go with the factory approach. we must be able to instantiate each unit independently
then cohrere them.

yes.

construction then cohesion decoupled.

alrigth.

furthermore, the way were joining things right now is so messy and hard to document. its like, a
complex dance of mutable state flows, with no clear architectural boundaries. we had to define
the pipe in the hub class for it to make sense. this implies we need an intermediary type, which has
the sole purpose of joining two things

a flange?

yes, basically

a flange

what does a flange do?

given two connectiables, with directionality (e.g A -> B), it ensures the output of A is recieved by
B

and how does that impact connectibility?

the flange is responsible for reporting connected yes no.

how do they link together?

i suppose thats where connectible comes in.

A -> flange -> B -> flange -> C
               | -> flange -> D

D is transitely connected if flange reports connected and whatever flange is connectd to reports
all its flanges are connected

so its

connectible.isConnected = self.flange.isConnected

and flange.isConnected = upstreamHalf.isConnected

is that right?

yes.

might this get heavy?

yes but that's the price of lead.

the library will be called lead.

i dont care about memory constrained devices. i care about correctness. a modular, decoupled
framework for linking together pipelines.

a question i have.

since isconnected is a function of pipes and hubs etc, how do they know about thier flanges? if
pipe is some isolated thing, that gets created in isolated, then assembled later, how does it even
have a flange to reference?

i suppose it will ned a lifecycle callback

interface Connectible {
  suspend fun onConnection(flange: Flange)
}

is this an upwards connection? 

yes, you only get upwards connection notifications. 

and how do we connect things downwards?

ok so this is where each different type needs to be slightly different.

pipe will do

pipe.connect(foo)

or perhaps. ah!

flange is an implementation detail. users never construct flanges. they just do

pipe.append(hub)

append is responsible for creating a flange, connecting pipe at one side, and connecting hub at the
other.

how will that work? how will flange know what to connect?

hm...

need to work out the right api.

well lets differentiate between instantiation and connection. we can work out connection second
first, instantiation is realtively simple since most elements are barely configurable.


manifest.create() has no args

pipe.create(transform: (Flow<T>) -> Flow<R>)

outflow.create()

this is jsut the factory pattern. 

the hard part is assembling them.

i dont think we need a flange. flange implies theres some separate thing between nodes.

i think we just need hm.



lets go from the ux.

soi reata  bunch of components. 

then i join the components together

ah.

connection was only meaningful when the connections themselves were being asynchronously established

since we're assembling things now then joining them together, theres no need for asking

am i connected to root? at the end.

its structurally guarnteed to be the case

howso?

kotlin flows build from the bottom up. you define a chain of operators, but when you call
collect, they join upwards asynchronously. the whole point of sealant was to avoid the race between
the flow pipe construction and the signal to start emissions. since wer fundamentally eradicating
the need for recursive pipe construction, it allows programs to be constructed where the mere
existence of the outflow guarantees the pipeline is connected. the pipeline doesnt need to ask
"am i connected" at the end, becuase it would be a bug in the application if someone gave you 
a pipe that wasn't connected to anything.

two separte stages:

1. setup the pipes and give someone an outlet.
2. begin the flow and pipe data into the inlet.

so we dont need connectible?

well... this whole thing is actually kind of a wrapper around sealant in the end. it will still
be necessasry to ensure that within a pipe everything is connected, so pipes benefit from sealant,
but the broader structures themselves, dont need sealant guarantees. a pipeline is sealed by virtue
of existing.

hm, ok so this is not a replacement for sealant, its an abstraction over it.

exactly


sealant: implementation detail of pipe.

lead: broader framework for pipeline plumbing (avoids user needing to care about connection status)

how will that work now? right now sealant is coupled to the idea of hubs.

well a hub is really just one end of a pipe. what we need is to be able to say:

given some pipe with some transformation in it, will every value going into the top come out the
bottom? i.e. is the pipe leaky?

we added hub as an implementation detail of pipe. it was common that given flow x there would be N
observers of it.

how can we implement this sealed pipe idea then without a hub?

good question...

how do we ensure that when 

hub.append(pipe) returns, data flowing into hub will make it to the other side of teh pipe? more
importantly how do we do this so that hub and pipe are not coupled impl wise at all?

im a little confused by geminis suggestion of upstreams and downstreams.

well nevermind, it was from a time when we were considering connectibel to be a necessary detail

its no longer necessary . there is no connectible. a pipeline that exists is a pipeline that is
connected. or at the very least, an api that exposes a pipeline is well defined iff and only iff
it provides a connected pipe. by making append a suspedning function that only returns after the
connection is established then we ensure the pipeline is fully formed after some pipeline factory
resumes.

alright so that only leaves one issue:

the pipe connection issue.

how do we ensure a pipe is connected so we can return?

using a hub internally seems a little overkill. 

the hub had the right idea though. it accepted values into a mutable shared flow adn ensured it
was collecting. then it spawned a mutabel shared flow for each pipeline and pushed data int. then
each pipeline had its own msf it could reliably query for "is connected" from top to bottom.

what if pipe exposed an inlet and outlet flow like this


MSF -> pipe -> flow

so its like, instead of the hub providing pipe with a MSF, pipe exposes its MSF.

it shouldnt be a msf though. it should just be a receiver which synchronously pushes into a MSF.

MSF is just used as the hack in flows to get a subscriber signal.

agreed.

so, pipe API should be

interface Pipe<T> {
  suspend fun accept(t: T)
}

implementationis something like

class PipeImpl<T> : Pipe {
  private val receiver = MutableSharedFlow<T>()

  override suspend fun accept(t: T) {
    receiver.emit(t)
  }
}

when something does

hub.attach(pipe) how do we know when to resume? it should suspend.

good question.


in the old system connection was tied to observation. we were trying to say "when is observation
started, can i push values into the pipe yet?" not we're shifting the paradigm to:

if the pipe exists i can put values into it.

since the pipe might have disconnects in the internal flow, we still ned MSF to emit, so, the pipe
will have to expose a flow sill, and already be collecting when instantiated like this::

interface Pipe<T> {
  
  val outflow: Flow<T>

  suspend fun accept(t: T)

}

implementationis something like

class PipeImpl<T>(transformation: (Flow<T>) -> Flow<R> ) : Pipe {

  private val receiver = MutableSharedFlow<T>()

  override val outflow = receiver.transform(transofmration) // or whateer

  override suspend fun accept(t: T) {
    receiver.emit(t)
  }
}

striclty speaking, until somethign actually attaches to taht pipe, it is NOT safe to send values
down, because they will be lost. this is where the downstream comes in.

hold up isnt this just re-inventing kotlin sequences?

yes and no. yes because we're going top down, no becasue sequences operate sequentially, and require
each level to collect into a data structure. we're keeping the whole pipeline working
asynchronouysly just with some safety.


back on track



where are we at?





i have hit a snag.

if connect() cannot return until pipe is connected, but pipe depends on an outlet being attached
to be connected, then connect() will not return until the entire downstream pipeline has been
connected. thats not ideal.

what i need is:

data that goes into the pipe will absolutely come out the other end.

we can achieve this with a MSF at the top and a collector at the end. the pipe is just an
encapsulated boundary to prevent leaky transformations.

accept -> MSF -> pushed into transform -> collected -> MSF -> outflow

this will probably have various peformance issues. one of the benefits of flows is that collect is
just a suspending function calling up the pipe. 

it's not called lead because its lightweight.

alright fair enough

so that solves the assembly issue.

once a pipe exists its actively collecting the inflow, pushing it through the transform, and
depositing it back itno a MSF that is acting as the outflow. well continue to make it closable
so when its no longer needed it can be discarded.

hm... gemini says theres an issue with MSF: it does not actually suspend and emit if there are no
collectors. that is an issue becasue it parks us right back where we started. how do we know when
the final downstream collector is collecting?

well.. once outflow is attached why si that a problem? once something is attached downstream of the
pipe it will begin collecting.

this is irritating.

yes but its always irritating until we get there. in teh end this will be a very nice api.

im so far disconnected fromoksp etc righ tnow, 

alright were almost there.

quinn is great. it solves the core issues of getting an async engine plumbed into a sync thing
coroutines etc were already good.
sealant, while nice, does not solve the problem. we cant proceed with it. we could hack it in, but
weve come this far.
obelisk and oksp are very nice, will jsut need some tidying.

yes what concerns me is i lost track of some of geminis changes at one point, now i will need to
comb through carefully to ensure everytihng is still how i like ti. well thats the case anyway.

lets just soom out.

so

pb + quinn + kale -> oksp -> obelisk -> backstab

thats the stack i think.

so lets get this sorted then.

so lets see, the api in focus is more of a funcitonal api than anything. im not expecting the user
to actually care about the details of pipe. they just create one with the factoyr.

theres a name for this pattern i think

anyway, the idea is: once all the pipeline elements are connected, it should be impossible to leak.

- few questions left what happens if the pipeline needs to be disassembled or modified after
construction? answer: we need to decide whether pipelines etc are reusable or single use only. can
you recycle components? maybe. makes them stateful. probably better if they are destroyed/closed to
avoid accidental use. safety vs performance as usual. choose safety.
- is there any guarantee the pipeline is connected? no, but thats ok. the guarntee of connectedness
was an issue from flows. they wre inherently lossy. you could setup your application wtih perfect
flow logic, and get hit with an async race condition when you collect. if you can setup flows right
you can setup a pipeline.


alright this is all coming together. so we really have:

1. a series of pre-fabricated components that do pre-specified things. they are differentiated by
their pipe shape (fan out, fan in, etc)
2. a configurable pipe. this is differentiated by the use supplied logic that goes in

things in cateogyr 1 arent configurable. its just factory.create() gives you a new one
pipe is configurabley by teh flow logic that goes in factory.create { flowTransformerLogic }

what are the components we have

inlet: values pushed in from some regular programming construct (for loop etc). push in is
suspending for backpressure.
output: values pushed out from the pipeline to some regular programmign construct. a listener 
basically.
pipe: applies a transform to data flowing in
manifold: fans data out from 1 intake to N pipes
junction: marges data from N pipes to one outflow
junctifold: manifold bolted onto a junction

ah junction will need to be configurable with a flow merger too