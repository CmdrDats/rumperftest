(ns perftest
  (:require
    [rum.core :as rum]
    [clojure.string :as str]))

(def wordlist
  ["Lorem" "ipsum" "dolor" "sit" "amet" "consectetuer" "adipiscing" "elit" "Maecenas" "porttitor" "congue" "massa" "Fusce" "posuere" "magna" "sed" "pulvinar" "ultricies" "purus" "lectus" "malesuada" "libero" "sit" "amet" "commodo" "magna" "eros" "quis" "urna" "Nunc" "viverra" "imperdiet" "enim" "Fusce" "est" "Vivamus" "a" "tellus" "Pellentesque" "habitant" "morbi" "tristique" "senectus" "et" "netus" "et" "malesuada" "fames" "ac" "turpis" "egestas" "Proin" "pharetra" "nonummy" "pede" "Mauris" "et" "orci" "Aenean" "nec" "lorem" "In" "porttitor" "Donec" "laoreet" "nonummy" "augue" "Suspendisse" "dui" "purus" "scelerisque" "at" "vulputate" "vitae" "pretium" "mattis" "nunc" "Mauris" "eget" "neque" "at" "sem" "venenatis" "eleifend" "Ut" "nonummy" "Fusce" "aliquet" "pede" "non" "pede" "Suspendisse" "dapibus" "lorem" "pellentesque" "magna" "Integer" "nulla" "Donec" "blandit" "feugiat" "ligula" "Donec" "hendrerit" "felis" "et" "imperdiet" "euismod" "purus" "ipsum" "pretium" "metus" "in" "lacinia" "nulla" "nisl" "eget" "sapien" "Donec" "ut" "est" "in" "lectus" "consequat" "consequat" "Etiam" "eget" "dui" "Aliquam" "erat" "volutpat" "Sed" "at" "lorem" "in" "nunc" "porta" "tristique" "Proin" "nec" "augue" "Quisque" "aliquam" "tempor" "magna" "Pellentesque" "habitant" "morbi" "tristique" "senectus" "et" "netus" "et" "malesuada" "fames" "ac" "turpis" "egestas" "Nunc" "ac" "magna" "Maecenas" "odio" "dolor" "vulputate" "vel" "auctor" "ac" "accumsan" "id" "felis" "Pellentesque" "cursus" "sagittis" "felis" "Pellentesque" "porttitor" "velit" "lacinia" "egestas" "auctor" "diam" "eros" "tempus" "arcu" "nec" "vulputate" "augue" "magna" "vel" "risus" "Cras" "non" "magna" "vel" "ante" "adipiscing" "rhoncus" "Vivamus" "a" "mi" "Morbi" "neque" "Aliquam" "erat" "volutpat" "Integer" "ultrices" "lobortis" "eros" "Pellentesque" "habitant" "morbi" "tristique" "senectus" "et" "netus" "et" "malesuada" "fames" "ac" "turpis" "egestas" "Proin" "semper" "ante" "vitae" "sollicitudin" "posuere" "metus" "quam" "iaculis" "nibh" "vitae" "scelerisque" "nunc" "massa" "eget" "pede" "Sed" "velit" "urna" "interdum" "vel" "ultricies" "vel" "faucibus" "at" "quam" "Donec" "elit" "est" "consectetuer" "eget" "consequat" "quis" "tempus" "quis" "wisi" "In" "in" "nunc" "Class" "aptent" "taciti" "sociosqu" "ad" "litora" "torquent" "per" "conubia" "nostra" "per" "inceptos" "hymenaeos" "Donec" "ullamcorper" "fringilla" "eros" "Fusce" "in" "sapien" "eu" "purus" "dapibus" "commodo" "Cum" "sociis" "natoque" "penatibus" "et" "magnis" "dis" "parturient" "montes" "nascetur" "ridiculus" "mus" "Cras" "faucibus" "condimentum" "odio" "Sed" "ac" "ligula" "Aliquam" "at" "eros" "Etiam" "at" "ligula" "et" "tellus" "ullamcorper" "ultrices" "In" "fermentum" "lorem" "non" "cursus" "porttitor" "diam" "urna" "accumsan" "lacus" "sed" "interdum" "wisi" "nibh" "nec" "nisl" "Ut" "tincidunt" "volutpat" "urna" "Mauris" "eleifend" "nulla" "eget" "mauris" "Sed" "cursus" "quam" "id" "felis" "Curabitur" "posuere" "quam" "vel" "nibh" "Cras" "dapibus" "dapibus" "nisl" "Vestibulum" "quis" "dolor" "a" "felis" "congue" "vehicula" "Maecenas" "pede" "purus" "tristique" "ac" "tempus" "eget" "egestas" "quis" "mauris" "Curabitur" "non" "eros" "Nullam" "hendrerit" "bibendum" "justo" "Fusce" "iaculis" "est" "quis" "lacinia" "pretium" "pede" "metus" "molestie" "lacus" "at" "gravida" "wisi" "ante" "at" "libero" "Quisque" "ornare" "placerat" "risus" "Ut" "molestie" "magna" "at" "mi" "Integer" "aliquet" "mauris" "et" "nibh" "Ut" "mattis" "ligula" "posuere" "velit" "Nunc" "sagittis" "Curabitur" "varius" "fringilla" "nisl" "Duis" "pretium" "mi" "euismod" "erat" "Maecenas" "id" "augue" "Nam" "vulputate" "Duis" "a" "quam" "non" "neque" "lobortis" "malesuada" "Praesent" "euismod" "Donec" "nulla" "augue" "venenatis" "scelerisque" "dapibus" "a" "consequat" "at" "leo" "Pellentesque" "libero" "lectus" "tristique" "ac" "consectetuer" "sit" "amet" "imperdiet" "ut" "justo" "Sed" "aliquam" "odio" "vitae" "tortor" "Proin" "hendrerit" "tempus" "arcu" "In" "hac" "habitasse" "platea" "dictumst" "Suspendisse" "potenti" "Vivamus" "vitae" "massa" "adipiscing" "est" "lacinia" "sodales" "Donec" "metus" "massa" "mollis" "vel" "tempus" "placerat" "vestibulum" "condimentum" "ligula" "Nunc" "lacus" "metus" "posuere" "eget" "lacinia" "eu" "varius" "quis" "libero" "Aliquam" "nonummy" "adipiscing" "augue"])

(rum/defc test-component < rum/reactive {:key-fn (fn [idx _] (str "comp" idx))}
  [idx component-atom]
  (let [component (rum/react component-atom)]
    [:div {:style {:font-size "2pt" :float "left" :height "100px" :width "50px"}} [:h1 (:title component)]
     [:p
      (:text component)]]))

(defn gen-component [n]
  (let [text
        (->>
          wordlist
          shuffle
          cycle
          (take 130)
          (str/join " "))]
    {:text text
     :title (str "Component " n " - " (count text))
     :count (count text)}))

(defonce app-atom
  (atom
    {:components
     (vec
       (map gen-component (range 200)))
     :compidx (rand-int 200)}))

(rum/defc root < rum/reactive []
  (let [compcount-atom
        (rum/derived-atom
          [app-atom]
          ::compcount
          (fn [app] (count (:components app))))
        compcount (rum/react compcount-atom)
        compidx-atom (rum/cursor app-atom :compidx)
        compidx (rum/react compidx-atom)
        selected-comp-title-atom (rum/cursor-in app-atom [:components compidx :title])]
    [:div
     [:div "Rum performance test."
      [:br]

      [:button
       {:on-click
        #(swap! compidx-atom (rand-int compcount))}
       "Random Element"]

      "Element " (str compidx) " "
      [:input {:value (rum/react selected-comp-title-atom) :on-change #(reset! selected-comp-title-atom (.-value (.-target %)))}]]

     (into
       [:div]
       (map
         #(test-component % (rum/cursor-in app-atom [:components %]))
         (range compcount)))]))

(rum/mount (root) (.getElementById js/document "mnt"))