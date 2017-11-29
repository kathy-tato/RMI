# rmi-playground
A java RMI proof of concept.

#### Why would I need this?
It seems to be many ways of developing an RMI application. There are many guides in the internet but most of them are outdated (some of them still use `rmic` in their examples). The best one I found is the official Oracle's [Trail: RMI](https://docs.oracle.com/javase/tutorial/rmi/index.html) and you should head there first to understand the context of this playground.

#### So, if Trail: RMI is so good, why making yet another example?

Thing is, `Trail: RMI` showcases a feature called `code base` in order to download code from a remote machine and run it locally in case the source code is missing when a remote invocation is exetuted and it may be a real headache to configure it properly to make it work, and truth be told, in many cases the aforementioned feature is not even required because common source code used by different hosts can be manually distributed as isolated libraries (e.g. `.jar`).

#### How do I run the example code?

In the root of the project there is a minimal `Makefile`; it's targets are:

- `out`: contents from *src* are copied into *out* directory.
- `compute`: compiles the *compute* pacakage as *out/compute.jar*
- `computations`: compiles the *computations* package as *out/computations.jar*
- `engine`: compiles the *engine* package
- `client`: compiles the *client* package
- `run-rmiregistry`: runs the `rmiregistry` with *CLASSPATH* set to *out/compute.jar*
- `run-engine`: runs the *engine* package
- `run-client`: runs the *client* package

TL;DR

##### Compilation && Execution

```sh
make

# Keep running rmiregistry
make run-rmiregistry

# Run rmi server
make run-engine

# Run rmi client
make run-client
```



