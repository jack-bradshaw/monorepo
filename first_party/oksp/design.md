ok so i want to extract the ksp stuff into a separate library so it can be reused.

how will this work?

what if there were a ksp componet like this

interface KspComponent {
  fun processor(): 
}

halt. do we expose the processor to the component? what is the component really? how does the
system integration work? why not simply implement a ksp directly.

implementing ksp directly has problems: the program structure is inherently round based, requireing
boilerplate and custom logic (complex) to setup a simple reactive flow processor. the ksp stuff
in backstab ins an exaple. it would be better if ksp were just some abstrated program root that the
downsteam library doesnt need to worry about, they can get symbosl from each round, and they can
get a start/stop signal from for process lifecycle.

alright so the goal is to absract away the actual KSP foundation so the downstream can write
reactive flow-based logic for their processro. will the API be dagger based?

yes of course, it should be, it maeks integration simple.

then we need to choose:

a. the componetn interface
b. the foundation api that consumers can implement to get control
c. a means for declaring the root (similar to how is the appplication class declared on android
will there be a manifest?)

why is this any better than ksp?

ksp has the problem of coupling the entry point to the data inflow, it provides information in
synchronous rounds, and a bunch of other architectural issues.

lets perhaps consider the ideal scenario how about something like

class MyApplication : Main {
  override fun onCraete() {

  }
  
  override fun onStart() {

  }

  override fun onStop() {

  }
}

whats the actual difference though? why do all this? what does this api do better?

it focuses on abstract lifecycle that can do anything, which is what the application root should do, instead of a spcific task, processing symbols, because real systems are generally far more complex.
also it abstracts the details of processing (rounds etc) behind a convenient flow-based asynchronous
api for advanced concurrent programming.

how do we get the processor etc

pass in a context object to onCreate() that has the processor asyn capi