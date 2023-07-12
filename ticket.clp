(deftemplate bilet-info
    (slot kwota-bazowa)
    (slot oplata-za-psa)
    (slot znizka-duza-rodzina)
    (slot ilosc-biletow)
    (slot znizka-happy-hours)
)

(defrule kwota-bazowa-klasa1-ponizej100 ""
    (klasa 1)

    (odleglosc ?odleglosc)
    (test (< ?odleglosc 100))
    =>
    (assert (kwota-bazowa 43))
)

(defrule kwota-bazowa-klasa1-powyzej100 ""
    (klasa 1)

    (odleglosc ?odleglosc)
    (test (> ?odleglosc 99))
    =>
    (assert (kwota-bazowa 73))
)

(defrule kwota-bazowa-klasa2-ponizej100 ""
    (klasa 2)

    (odleglosc ?odleglosc)
    (test (< ?odleglosc 100))
    =>
    (assert (kwota-bazowa 33))
)

(defrule kwota-bazowa-klasa2-powyzej100 ""
    (klasa 2)

    (odleglosc ?odleglosc)
    (test (> ?odleglosc 99))
    =>
    (assert (kwota-bazowa 57))
)



;----------------------------------------------------------------

(defrule przewoz-psa-TAK ""
    (przewoz-psa TAK)
    =>
    ;(assert (bilet-info (przewoz-psa TAK)))
    (assert (oplata-za-psa 15.20))
)

(defrule przewoz-psa-NIE ""
    (przewoz-psa NIE)
    =>
    ;(assert (bilet-info (przewoz-psa NIE)))
    (assert (oplata-za-psa 0))
)

(defrule ilosc-osob ""
    (ilosc-osob ?ilosc-osob)
    =>
    (assert (ilosc-osob ?ilosc-osob))
)

(defrule karta-duzej-rodziny-tak1 "";od 1 do 4
    (ilosc-osob ?ilosc-osob)
    (test (> ?ilosc-osob 1))
    (test (< ?ilosc-osob 5))

    (karta-duzej-rodziny TAK)
    =>
    (assert (znizka-duza-rodzina 0.3))
)

(defrule karta-duzej-rodziny-tak2 "";od 1 do 4
    (ilosc-osob ?ilosc-osob)
    (or (test (> ?ilosc-osob 4)) (test (< ?ilosc-osob 2)))

    (karta-duzej-rodziny TAK)
    =>
    (assert (znizka-duza-rodzina 0.0))
)

(defrule karta-duzej-rodziny-nie1 "";od 2 do 4
    (ilosc-osob ?ilosc-osob)
    (or (test (> ?ilosc-osob 4)) (test (< ?ilosc-osob 2)))

    (karta-duzej-rodziny NIE)
    =>
    (assert (znizka-duza-rodzina 0.0))
)

(defrule karta-duzej-rodziny-nie2 "";od 2 do 4
    (ilosc-osob ?ilosc-osob)
    (test (> ?ilosc-osob 1))
    (test (< ?ilosc-osob 5))

    (karta-duzej-rodziny NIE)
    =>
    (assert (znizka-duza-rodzina 0.0))
)

(defrule happy-hours-nie ""
    (happy-hours ?happy-hours)
    (test (> ?happy-hours 18))
    =>
    (assert (znizka-happy-hours 0.0))
)

(defrule happy-hours-tak ""
    (happy-hours ?happy-hours)
    (test (< ?happy-hours 20))
    =>
    (assert (znizka-happy-hours 0.1))
)

(defrule uzupelnij-dane ""
    (kwota-bazowa ?kwota-bazowa)
    (oplata-za-psa ?oplata-za-psa)
    (znizka-duza-rodzina ?znizka-duza-rodzina)
    (ilosc-osob ?ilosc-osob)
    (znizka-happy-hours ?znizka-happy-hours)
    =>
    (assert
        (bilet-info
            (kwota-bazowa ?kwota-bazowa)
            (oplata-za-psa ?oplata-za-psa)
            (znizka-duza-rodzina ?znizka-duza-rodzina)
            (ilosc-biletow ?ilosc-osob)
            (znizka-happy-hours ?znizka-happy-hours))
    )
)

;(defrule uzupelnij-dane
;    (forall (bilet-info (kwota-bazowa ?kwota-bazowa) (oplata-za-psa ?oplata-za-psa) (znizka-duza-rodzina ?znizka-duza-rodzina) (ilosc-osob ?ilosc-osob))()
;=>
 ;   (assert
;        (bilet-info
;            (kwota-bazowa ?kwota-bazowa)
;            (oplata-za-psa ?oplata-za-psa))
;            (znizka-duza-rodzina ?znizka-duza-rodzina)
;            (ilosc-biletow ?ilosc-osob)
;    )
;)

(deffunction MAIN::get-ticket-info ()
	(find-all-facts ((?f bilet-info)) TRUE)
)