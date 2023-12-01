(ns advent.repl)

(comment
  (require '[portal.api :as portal])

  (def p (portal/open))

  (add-tap #'portal/submit) 

  (tap> :x)

  )

