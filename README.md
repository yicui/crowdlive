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

####Core

This module mainly consists of three packages. The *domain* package contains all basic domain entity beans, such as
Audio, Video, Image, etc. The *algo* package houses all the Filter classes, each of which implements a basic 
processing functionality, such as Fourier transformation, finding order statistics, finding maximum/minimum, etc. 
You can feel free to devise your algorithm by freely combining these filters. The *io* package is responsile to read in
audio files of different formats and write out video files. This module is pure POJO, which is always a pleasure to code.
I am largely done except the video creation part. All classes are unit-tested.

####Desktop

This module mainly consists of two packages. The *charting* package contains all classes visualizing the audio data
and its processing result. The *gui* package contains all GUI widgets. I choose Spring Rich Client 1.1.0 to save my time
on layout design and widget creation. But ironically its configuration cost a lot more time than expected. But I think
I am getting there.

####Web

I haven't worked on this module yet. When I do, I hope Spring Roo can deliver what it says it can deliver. This can 
grow out to a full-fledged website, but I'll just realize the very baseline here. The story will be a user uploads an
audio file (WAV only as of now), then browses the image gallery to choose set of images he/she wants to showcase, selects
"looping" or "shuffling", finally clicks "Create Video". This will trigger the algorithm to process the incoming audio,
insert gifs as visual cues, and upload the resulting video to YouTube. Also each user can see from her account
the audio she has uploaded and all her video creations. Users are also welcomed to enrich the image gallery.