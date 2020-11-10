othello
=======

Jeux d'othello développé en Java.
Testé sous windows 10 avec Java 8.

![Screenshot](/doc/othello_capture.PNG?raw=true "Exemple othello")

Ce projet date de fin 2009 - debut 2010.
Il m'a permis de tester une IA avec réseau de neuronnes.

Avec l'ihm, on peut jouer entre 2 joueurs humains ou contre une IA.
On peut aussi faire jouer 2 IA l'une contre l'autre.
Le niveau d'IA est très faible.

J'ai essayé de voir s'il était possible d'utiliser un réseau de neuronnes pour l'IA, mais je n'ai pas réussi à l'implémenter.
L'IA est basé soit sur de l'Alpha/Beta, soit du min/max, soit des algorithmes très simples (par exemple selection d'une case au hasard).

Pour le test d'un réseau de neuronne, j'ai utilisé le livre ['Réseaux neuronaux et modèle objet'](http://www.rennard.org/irn/)
de Jean-Philippe Rennard Vuibert, 2006 ISBN 2-717-4830-8 [Amazone](https://www.amazon.fr/R%C3%A9seaux-neuronaux-introduction-accompagn%C3%A9e-mod%C3%A8le/dp/2711748308)
C'est un bon livre qui explique bien les réseaux de neuronne.

Les IA implémenté sont :
* Simple 1 : Recherche de la première case de disponible en commencant par en haut à gauche
* Simple 2 : Recherche de la case disponible qui mange le plus de cases
* Simple 3 : Recherche de la case disponible qui mange le plus de cases (autre methode de calcul)
* MinMax 1 : [Algorithme MinMax](https://fr.wikipedia.org/wiki/Algorithme_minimax) de profondeur 1
* MinMax 2 : [Algorithme MinMax](https://fr.wikipedia.org/wiki/Algorithme_minimax) de profondeur 2
* MinMax 3 : [Algorithme MinMax](https://fr.wikipedia.org/wiki/Algorithme_minimax) de profondeur 3
* AlphaBeta 1 : [Algorithme Alpha-Beta](https://fr.wikipedia.org/wiki/%C3%89lagage_alpha-b%C3%AAta) de profondeur 1
* AlphaBeta 2 : [Algorithme Alpha-Beta](https://fr.wikipedia.org/wiki/%C3%89lagage_alpha-b%C3%AAta) de profondeur 2
* AlphaBeta 3 : [Algorithme Alpha-Beta](https://fr.wikipedia.org/wiki/%C3%89lagage_alpha-b%C3%AAta) de profondeur 3

Pour les algorithme MinMax et AlphaBeta, le score consiste en comptant le nombre de case de la couleur du joueur, 
moins le nombre de case de la couleur du joueur adverse.

Build
=====
Pour builder le projet :
```shell
mvn clean install
```

Execution
=====

Pour lancer le jeux, il faut executer :
```shell
run.bat
```
ou
```shell
java -jar othello-1.2.0.jar
```

