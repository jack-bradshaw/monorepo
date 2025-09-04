visibility("//...")

SEPARATOR = Label("@separator").workspace_name.removesuffix("separator")[-1]

def _impl(_):
    pass

separator = repository_rule(implementation = _impl)
