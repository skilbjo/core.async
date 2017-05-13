(defproject core-async "0.1.0-SNAPSHOT"
  :uberjar-name "core-async.jar"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-time "0.12.2"]
                 [org.clojure/core.async "0.3.442"]
                 [environ "1.1.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :jvm-opts ["-Xms256m" "-Xmx256m" "-XX:MaxMetaspaceSize=128m"
             "-client" "-Duser.timezone=PST8PDT"
             "-Dclojure.compiler.direct-linking=true"
             "-XX:-OmitStackTraceInFastThrow"])
