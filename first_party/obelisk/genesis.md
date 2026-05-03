ok whats obelisk?

the core architecture of backstab is good, but the problem is... its not just "business logic".
right now oksp provides the foundation for a nice reactive ksp application, and backstab uses it
with a dual layer strategy:

- core: just the core business logic of backstab. completely isolated from ksp, does not use even
the types. its completely decoupled. its own model, its geneation logic, etc. it processes an inflow
of symbols in its own model, creates other symbols in its own model, and publishes them.
- ksp: interface between core and oksp. adapts various ksp types into core model, provides adapters
that translate the oksp services in the core services (presently alled repositories but nevermind).

and whats the issue?

lets say i start building more annotation processors, and i have plans to do exactly that. there are
elements of this design which would be copy pasted, meaning that backstab is not just backstab,
its backstab plus various pieces of boilerplate that bridge the core of backstab to ksp.

this is an issue because?

this is an issue because:

1. it obscures the actual logic of backstab. it's hard to inspect backstab and get a sense of what
exactly its logic is. there is noise in the way. this makes readability and maintainability hard.
2. its unmaintainable. if i have N different end annotation processors, i would have to run upgrades
across all of them if theres a bug in the shared (copied) logic. this is software engineering 101,
DRY.
3. its confusing. the whole system is confusing. its doing too much.

so, i need to effectively isolate all the elements of backstab that could be generic then design
a minimal api that consumers can use. it has to reduce upwards complexity so that in the end,
the user (i.e. backstab) is getting exactly what they need and nothing more, nothing less. in order
for it to be useful though, it cant just be an abstraction over the oksp application, it has to be
focused.

focused, like light?

yes exactly.

and then Kane said:

"LET THERE BE (an obelisk of) LIGHT!"

yes very good. so, obelish must be:

a code generation abstraction system that is independent of the underlying generation system (ksp,
kapt, etc), structured so that it makes adapting to a logcal model/system easy. this is going to be
a tight api, no bells and whistles, just eactly what you need to mount an abstract symbol processor
on someone else's compiler plugin pipeline. ksp is just one port.

alright so heres the tricky part

obelisk application is a generic interface. its not coupled to any base provider. if this is to
work as intended, the appliaton cant have to worry about what its based on. the end user should be
able to write an application that expects foo and writes bar, and thats it. the based arent
application aware though... they are just roots that instantiate application and provide it with
values. they read native values from their systems, in order to provide the nicely formed foo, they
require a translator. this means:

translators bridge the gap from base to application, but a translate is constrained by two
properties: what is translates to, and what it translates from. it requires both application
awareness and base awareness. it cant be defined in the base though, becuase the base cannot be
aware of teh higher layer, so... it will need to be defined in the application. so applications will
need to be aware of which bases they support. if application A requires foo and produces bar, it
will need to provide a translator that turns base types into foo and turns bar types into base
types so the base can use it to provide and receive the values the application needs/produces. it
means each application will need to produce N translator, but only if it intends to be used with
N bases. In practice it will probably only be a handful, and they will be created over years as the
application is ported to new bases. in some scenarios they might be shared, but in general, they are
application-specific, and thus, are defined by applications:

so an applciation that expects foo, produces bar, and works on base that has AST root type of T and
can publish values of R will need to provide a root -> foo converted.

i guess the output though... should always be a kotlin source file. theres really no scenario in
which case the output conversion is unnecessary that part can be handled by the base.

so:

1. application provides input parser
2. base contains static source file to native source type parsing

