I have encountered the belief amongst software engineers that tests should always be divided into
setup/act/assert stages, and while there is value in this practice, it is sometimes taken to an
unreasonable extreme, with no newlines permitted between the individual setup/act/assert blocks. I
believe the separation of tests into these logical phases is sensible, sometimes, as it creates
rigour and maintainability, but strictly compressing all code into undivided blocks is detrimental
to readability, and there are circumstances where the inherent program structure makes this approach
infeasible (build system integration tests, for example, sometimes require distributing logic across
multiple build files and workspaces). If there are occasions when rules do not apply though, then
what are we to do when our well-intentioned peers argue for them to an unreasonable degree? Moreover
what do we do when we find ourselves playing the advocate for stringent rules? I myself have been
there on occasion.

In this particular case (test structure) we must balance the underlying principle (separation of
test stages) with pragmatic consideration of external constraints and other requirements
(readability and system limitations), but I suppose, seeking balance between pragmatism and idealism
extends far beyond this extremely narrow case, and is a core element of engineering in general. From
the Latin "Radix pragmatismi est taedium ideologiae inflexibilis et dogmatis rigidi": The root of
all pragmatism is weariness of inflexible ideology and rigid dogma. I argue that idealism is a noble
goal, one we should uphold when possible, but argue pragmatism in the fundamental root of
engineering, not principle, for pragmatism alone survives when idealism fails. In principles we find
direction, but in sensibility we find survival.

I want to pre-empt the most valid response to this position: Principles are eternal and absolute by
their very nature, so what reason could we ever have to deviate from them? The open-closed
principle, for example, does not state that classes should be open to extension and closed to
modification on Tuesday through Friday (national holidays excepted), it provides an absolute and
universal standard with iron-clad reasoning to back it up. We keep our classes this way because it
sustains software over time and adapts well to change. If principles are absolute and timeless, then
why would we ever deviate? Several answers exist, but most pragmatically: The existence of legacy
code often forces our hand and prevents perfect compliance with principles; furthermore, in complex
organisations there are emergent dynamics that are hard to predict and compensate for. One might
argue that such legacy code could have been written better to begin with, that refactoring legacy
should occur before adding more complexity, and that emergent behaviours should be caught by
automated tests; I would agree with this position, given infinite time; however, unfortunately
nature is not based on adherence to such principles, and occasionally, our principles are in
conflict with circumstances beyond our control.

We can stand on principle for a long time, but eventually, we will drop dead of starvation if we are
unable to eat, so while it is far more sustainable to follow engineering principles in the
long-term, the nature of an entropic universe dictates a more nuanced approach. We live in a world
where the mistake, the unexpected, and the undesirable all occur around us without our consent, and
we must adapt to that reality. We can use principle to mitigate these forces as best we can, and
when applied consistently they compound to work effectively, but we cannot achieve perfection, and
when the hull buckles through no fault of our own, a temporary patch is better than a sinking ship.
Less poetically, keeping production green is more important than checking abstract boxes, and we
must occasionally compromise our desire for perfection to sustain ourselves in the long-term,
especially in emergencies. We should of course be wary of those who would argue for short-term gains
without justification, as greed is far inferior to both idealism and pragmatism, but when we are
forced to make hard decisions through no fault of our own, engineers should choose realistic
pragmatism over dogmatic idealism.

In essence, I am arguing that principled idealism is the desirable end state of engineering, and we
must do everything to achieve it, but standing on principle alone when nature is bearing down on us
with entropy is untenable. We must prioritise pragmatism over idealism when intractable conflict
arises, and remember that engineering does not exist to serve abstract principles; quite the
opposite: ideals are merely tools engineers use to solve problems, build systems, and manage
complexity. If we could eliminate entropy entirely then it might be a different story, but we have
yet to devise a machine capable of this, so for now we will just have to make do with the occasional
compromise, and remain forever aware that weariness of inflexible ideologies and dogmatic thinking
is the root of all pragmatism. Remain weary, and remain sharp.
