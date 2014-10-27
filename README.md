Aeronautical Event Service
==========================

Build targets
-------------

* Clean: `mvn clean`
* Test: `mvn test`
* Run AES producer: `mvn -Pproducer package exec:java`
* Run AES consumer: `mvn -Pconsumer package exec:java`
* Run WSN broker: `mvn -Pbroker exec:java`

Git workflow
------------

* Every commit in the `master` branch should compile and the tests should pass without error
* Work directly in `master` for small changes
* If your copy of `master` diverges from the origin, rebase your changes:
  * `git pull --rebase`
* Implement large changes in a feature branch named "<your username>/<your feature>" (e.g. robinjam/testfeature):
  * `git checkout -b <your username>/<your feature>`
* Once the feature is complete, rebase your branch onto master and perform a non-fast-forward merge:
  * `git checkout master`
  * `git pull`
  * `git rebase master <your username>/<your branch>`
  * `git merge --no-ff <your username>/<your branch>`
