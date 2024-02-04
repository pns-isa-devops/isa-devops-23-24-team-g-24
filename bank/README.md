# Description de la Bank : 

Pour l'instant, la bank à été copiée-collée depuis le dépôt de la cookiecutter. Elle n'a pas été modifiée.

Le healthcheck est fonctionnel, il permet de vérifier que la bank est bien en ligne.

## Fonctionneemtn des fichiers dans /src: 
- main.ts : 
    - Le fichier main.ts est le fichier principal de la bank. Il permet de lancer la bank.
- app.controller.ts : 
    - Le fichier app.service.ts est le fichier qui permet de gérer les services de la bank. Il appelle le fichier app.service.ts
- app.service.ts :
    - C'est le fichier qui contient toutes les fonctions qui possèdent le code métier en profondeur
- app.controller.spec.ts : 
    - C'est le fichier qui permet de tester les fonctions du fichier app.controller.ts

## Fonctionnement des dossiers dans /src:
- /dto : 
    - Ce dossier semble contenir des classes qui seront utilisées comme "Objet" dans la banque
- /exceptions : 
    - Ce dossier contient les différentes exceptions qui peuvent être levées par la banque
- /health :
  - Ce dossier contient les fichiers qui permettent de gérer le healthcheck de la banque

## Comment installer : 
```shell
$ npm install
```

## Comment lancer : 
```shell
# development
$ npm run start

# watch mode
$ npm run start:dev

# production mode
$ npm run start:prod
```
## Comment tester (PAS FONCTIONNEL ATM CAR PAS LES BONS TESTS: 
```shell
# unit tests
$ npm run test

# e2e tests
$ npm run test:e2e
    
# test coverage
$ npm run test:cov
```
