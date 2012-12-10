Background
----------
This project had an interesting beginning, when I chatted with an executive of Universal Music Group (UMG) Nashville.
He thought it would be cool to have users' smartphones to flash different colors/patterns during a concert.
(remember those lighters?) This idea excited me. Then we brainstormed about many possibilities: when can we
expect near-field communication to happen; would indoor wi-fi be robust enough to reach all devices; what about
outdoor performances, etc, etc. The central issue is: we need a robust source to send control signals to smartphones.

Before long we came to an idea almost simultaneously: how about the soundwave itself? If the phone can catch those
incredibly loud sounds from a kick drum/cybal/baseguitar and act on them, then no infrastructure support is needed
at all. Plus, you don't have to teach musicians any technical stuff, the drumsticks are naturally the baton to 
coordinate smartphones.

So I started experimenting in my spare time. Thanks to [Nashville's music row](http://www.musicrow.com/), I never 
ran short of fieldtest grounds ([video](http://www.youtube.com/watch?v=qOe9eqCobt0) of one of my tests). But I soon
discovered this problem to be far more difficult than I expected. Instead of extracting music pattern from a piece of
recorded CD or mp3, we have to analyze signals sampled from an extremely noisy place, and the sampling devices are
in the hands of people, shaken and facing different directions with different distances & heights. And the algorithm must
act in real time, no luxury for any multi-pass refinement. So the next thing I made is a simulation program allowing me
to try different algorithms offline on the raw audio data sampled from the fieldtest. Then the best way to inspect the 
resultof any new algorithm is to create an animation showing the identified beats in some form of visual cues, meanwhile
playingback the audio it analyzes on, like [this example](http://www.youtube.com/watch?v=NYHUAHZvB6M) in which the
number indicates the actual audio frame index. Of course, the visual cue can be of any shape or color, like 
[this one](http://www.youtube.com/watch?v=cPQ_A8HPJp4).

One funny thing is, everytime I showed my colored-square video to my UMG friend, I can sense his dissatisfaction. 
This usually triggered a speech of mine on the great challenge of this problem and how fast we are improving. But then I
found out he was mostly complaining about the aesthetics of my visual presentation, Duh! We were on thinking different 
tracks, me on the accuracy of the algorithm and him on how amazing the app should look. Then it occured to me that a
tool like http://slides.com should be very helpful here: it leaves artists alone to try out various visual effects,
until they are happy with what they see.

A multi-module Project
----------------------
Right now the project is in a standstill since both of us have many duties with higher priorities to fulfill. Making
sense or not, I think it's better to share this idea, and carry it some extra miles to the point that my fellow
programmers can recognize what we've been doing.

Two groups of users might be interested in this project: *researchers* who want to design better beat recognition 
algorithm, and *artists* who want to create stunning videos from a piece of music. So it's quite natural to split the
project into two deliverables: a desktop simulator for researchers and a web portal for artists, both of which depend
on the core module including audio processing/analyzing functionalities. I chose Java Spring Roo 1.2 as my framework,
mainly for its rapid web application development capabilities.

****Core****

This module mainly consists of the *domain* 