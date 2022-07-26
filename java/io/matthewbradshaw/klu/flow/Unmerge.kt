package java.io.matthewbradshaw.klu.flow

fun mergeWithUnmerge()

// what i need is:
// when a descendant is attached, merge its flows into the transitive flows of this node
// when a descendant is detached, stop merging its flows into the transitive flows of this node
// do so without requiring the entire upstream source to reconnect
// example:
//
// descendants: a, b, c
// flow: + a + b + c
// descendant d added
// flow: + a + b + c + d
// descendant a removed
// existing flow: + a + b + c + d - a
// new flow: + b + c + d

// I really don't see a way around needing to resubscribe to the upstream.

// perhaps the mistake is trying to embed the flows in the nodes
// waht is each node just exposes its descendants as a flow, then the root node can be responsible for flattening the
// entire tree and adding/removing as requried
// that's probably cleaner