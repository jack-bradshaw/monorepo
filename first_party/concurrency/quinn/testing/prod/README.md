# Prod

This package exists because the Quinn testing infrastructure delegates to the Quinn production
infrastructure, but production and testing bindings cannot coexist in the same scope without
duplicate binding errors. The pass through component receives the production version via a component
dependency on the production component, the module rebinds it with a qualification, and the pass
through component exports the qualified binding, thereby creating a pass through that exposes the
production component with a binding. This allows the testing infrastructure to use the production
version without cluttering the production infrastructure with annotations that are only needed for
the testing infrastructure.
