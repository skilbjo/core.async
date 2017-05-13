# core.async

## Why
From (http://clojure.com/blog/2013/06/28/clojure-core-async-channels.html)[http://clojure.com/blog/2013/06/28/clojure-core-async-channels.html]

    There comes a time in all good programs when components or subsystems must stop
    communicating directly with one another.  This is often achieved via the introduction
    of queues between the producers of data and the consumers/processors of that data.
    This architectural indirection ensures that important decisions can be made with
    some degree of independence, and leads to systems that are easier to understand,
    manage, monitor and change, and make better use of computational resources, etc.

## Run

    lein run -m job.async

## Resources

- [ ] [http://clojure.github.io/core.async/](http://clojure.github.io/core.async/)
- [ ] [https://github.com/clojure/core.async/blob/master/examples/walkthrough.clj](https://github.com/clojure/core.async/blob/master/examples/walkthrough.clj)
