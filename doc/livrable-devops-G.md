# Plan de reprise d'activité

## Introduction

Dans le contexte de la reprise d'activité après un incident majeur, la rapidité et l'efficacité de la remise en service de notre infrastructure de CI/CD (Intégration Continue / Déploiement Continu) sont essentielles pour minimiser les perturbations et restaurer nos opérations à leur pleine capacité. Ce plan de reconstruction détaille les étapes nécessaires pour remettre en marche Jenkins et Artifactory, deux piliers de notre environnement de développement logiciel, afin de rétablir notre pipeline d'intégration continue et de déploiement continu dans les plus brefs délais.

Dans un objectif pédagogique et pour que tout le monde comprenne bien le document et les actions qu'il décrit, nous avons choisis de décrir toutes les actions nécessaires à la mise en place de notre ligne de CI/CD. Il pourra ainsi être utilisé comme aide mémoire pour la mise en place d'une ligne similaire. Dans le cas où ce document est utilisé pour la restauration ou la création d'une nouvelle ligne pour ce projet, les étapes qui ne sont pas liées à la VM ne sont pas à effectuer de nouveau mais à mettre à jour si nécessaire (github, smee, dockerhub, ...). Dans les informations à mettre à jour on compte la clef ssh permettant l'accès en lecture au repository. 

## Procédure de reconstruction

Cette procédure vise à recréer une infrastructure de CI/CD compatible à notre projet à partir d'un système linux vierge (Ububntu a été testé mais tout système avec les packages disponibles ferait l'affaire). Nous faisons l'hypothèse que la machine mise à disposition pour la remise en oeuvre de notre chaine de CI/CD est similaire à celle qui nous a été prétée (performances mais surtout packages pré-installés).

Pour commencer, copiez les fichiers dans le dossier PRA (docker-compose.yaml  start_jfrog.sh  start_smee.sh) vers un dossier de travail. C'est à cet endroit qu'il faudra effectuer les commandes.

### Jenkins

##### Setup du container

Nous utilisons l'image jenkins stockée sur docker hub et mappons le container sur le port 8000 de l'hote. Pour rappel, les ports autorisés au niveau de l'université sont compris entre 8000 et 8030.

```
docker pull jenkins/jenkins:lts
docker compose up -d
```

##### Configuration jenkins

- Création de l'agent

    L'agent a pour but d'exécuter les tests ordonnés par jenkins. La communication entre jenkins et l'agent se fait en ssh, il est donc nécessaire de générer une clef ssh par laquelle il est possible de se connecter au compte attribué à l'agent (ici jenkins). Attention car une clef ssh en sha256 n'est pas suffisante en termes de sécurité et sera refusée au niveau de jenkins, c'est pourquoi nous avons utilisé une clef ed25519.

    ```
    sudo useradd jenkins
    sudo mkdir -p /home/jenkins/.ssh
    sudo chown -R jenkins:jenkins /home/jenkins/.ssh
    cd /home/jenkins
    ssh-keygen -t ed25519
    sudo cat /home/jenkins/.ssh/id_ed25519.pub >> /home/jenkins/.ssh/authorized_keys
    sudo chmod 700 /home/jenkins/.ssh
    sudo chmod 600 /home/jenkins/.ssh/authorized_keys
    ```
    
    Une fois l'agent créé sur le système hote il faut aller le renseigner dans jenkins. Pour cela il faut aller dans Dashboard -> Manage Jenkins -> nodes -> new node
    Il faut spécifier :
    - le remote directory à /home/jenkins (ou le nom de l'utilisateur si changé) 
    - l'usage à ```use this node as much as possible```
    - launch method à ```launch agent via ```
        - host à 172.17.0.1
        - add credential : il faut mettre la clef ssh privée générée plus haut (tout le contenu du fichier nomDeLaClef).

- Création du pipeline

    Maintenant il faut créer un pipeline qui va cloner le repository, scanner ses branches et effectuer les étapes du jenkins file. Pour des raisons de simplicité notre jenkinsfile est stockée sur notre repository github, ce qui permet de simplement l'appeler. Jenkins devra avoir accès au repositoy au moins en lecture (uniquement en lecture pour des raisons de sécurité).
    
    Pour cela nous allons mettre en place une clef ssh avec un accès en lecture simple et pour des raisons de simplicité il s'agira de la même que celle permettant à accéder à l'agent jenkins. Pour cela, dans les paramètres du repository : aller dans deploy key -> add deploy key et coller la clef publique dans le champ prévu.
    
    Nous allons utiliser les webhooks de github sur tout push ou pull request pour lancer le build et les tests de la branche associée. Pour passer outre le par feu nous allons utiliser smee.io pour notifier jenkins.
    Pour cela, on commence par installer sur jenkins le plugin ```multibranch scan webhook trigger``` (Dashboard -> Manage jenkins -> Plugins -> Available plugins ; sélectionner le plugin, l'installer et redémarrer jenkins)
    ![alt text](https://cdn.discordapp.com/attachments/1022830713164279830/1225004643944239224/image.png?ex=661f8d7a&is=660d187a&hm=da2ca9bd0cb83b884fd941daa72efa45e12a289ff6a028eea1e57965b95ceb95&)
    Ensuite, il faut créer un canal sur smee.io et récupérer le lien du canal.
    Une fois le canal smee créé, on va créer un nouveau webhook dans les paramètres du repository github : Webhooks -> Add webhook ou edit webhook.
    
    Il faut spécifier :
    - Payload URL avec l'url du channel smee (ou cette URL qui est celle du channel smee originalement créé pour ce projet : https://smee.io/qtUEw6UwRKxYJA4 )
    - Choisir ```let me select individual``` et cocher ```pushes``` et ```pull requests```
    #
    ![alt text](https://cdn.discordapp.com/attachments/1022830713164279830/1225004048189624331/image.png?ex=661f8cec&is=660d17ec&hm=32abef7d0dcec259231c95ee5c87849665aeea2020614ddc6d2bbb7418487eda&)
    Pour créer le pipeline il faut aller dans Dashboard -> new item -> multibranch pipeline
    ![alt text](https://media.discordapp.net/attachments/1022830713164279830/1225002280500068352/Screenshot_from_2024-03-31_17-42-23.png?ex=661f8b47&is=660d1647&hm=7e78b415b1a6b70cdfeaafa0786c83942ef84f4678260ad9513cadc2b8c541d0&=&format=webp&quality=lossless&width=847&height=662)
    ![alt text](https://media.discordapp.net/attachments/1022830713164279830/1225002280760377394/Screenshot_from_2024-03-31_17-48-02.png?ex=661f8b47&is=660d1647&hm=8f3cb24abc05cfb64097a4dda92365c7039c0233b25a64c85016b9b0f72344a9&=&format=webp&quality=lossless&width=1046&height=662)
    Il faut spécifier :
    - Branch source à git
        - Credentials : les mêmes que pour l'agent ; ils devraient déjà exister.
    - Build configuration
        - Mode à ```by jenkinsfile```
        - Script à ```Jenkinsfile``` (le chemin d'accès au jenkinsfile depuis la racine du repository)
    - Scan multibranch pipeline triggers
        - scan by webhooks
        - trigger token à ```qtUEw6UwRKxYJA4``` ou le token donné par smee si nouveau channel smee
        ![alt text](https://cdn.discordapp.com/attachments/1022830713164279830/1225003684405186621/Screenshot_from_2024-03-31_17-54-27.png?ex=661f8c95&is=660d1795&hm=8e8833a8f0dca05c5677b22e5bad1748e064bd2a3289d62f52e4c6cf2de5ab20&)
    #
    
    Sur la machine hote, on doit installer le client smee :
    ```
    npm install --global smee-client
    ```
    Maintenant il faut l'exécuter sur le système hote. Pour ce faire, la commande est stockée dans le fichier ```start_smee.sh``` dans le dossier PRA. Attention, si le channel smee n'est plus l'original ou si l'adresse de l'hote n'est pas la même qu'actuellement alors il est nécessaire de modifier la commande contenue dans ce fichier (à savoir le paramètre --url avec l'URL du nouveau channel ; l'adresse de la machine hote)
    ```
    screen -d -m -S smee smee --url https://smee.io/qtUEw6UwRKxYJA4 -t http://vmpx07.polytech.unice.fr:8000/multibranch-webhook-trigger/invoke?token=qtUEw6UwRKxYJA4
    ```
    Si tous ces paramètres sont bons alors on peut lancer le script. Il faut également l'ajouter dans le crontab avec l'emplacement complet du fichier (```crontab -e``` va ouvrir l'éditeur vim par défaut. ```G``` permet d'arriver à la dernière ligne, ```o``` (minuscule) permet d'ajouter une ligne et d'entrer en mode édition, ```ctrl + maj + v``` permet de coller une ou plusieurs lignes, ```esc``` permet de quitter le mode édition, hors du mode édition ```:wq``` suivi de entrée permet de sauvegarder et quitter).
    ```
    ./start_smee.sh
    crontab -e
    ```
    Ligne à ajouter dans le crontab pour lancer le client smee au redémarrage de la machine (attention au chemin complet) :
    ```
    @reboot /home/teamg/start_smee.sh
    ```

### Artifactory

##### Setup du container

Le script ```start_jfrog.sh``` permet directement de pull l'image docker de jfrog 7.47.14 si elle n'existe pas sur la machine et de lancer le container.

Une fois le container lancé, il sera disponnible sur le port 8002 : http://vmpx07.polytech.unice.fr:8002 . Il sera nécessaire de créer un utilisateur admin à la première connexion.

Pour permettre à jenkins et artifactory de communiquer, il faut installer jfrog cli sur la machine hote :
```
wget -qO - https://releases.jfrog.io/artifactory/jfrog-gpg-public/jfrog_public_gpg.key | sudo apt-key add -
echo "deb https://releases.jfrog.io/artifactory/jfrog-debs xenial contrib" | sudo tee -a /etc/apt/sources.list && sudo apt update && sudo apt install -y jfrog-cli-v2-jf && jf intro
```

Il faut ensuite créer un nouveau repository : repositories -> add repositories -> local repository ; choisir un repository générique. (Celui-ci sera accessible dans le jenkins file en mettant /leNomDuRepository à la fin de la commande sh pour upload)
![alt text](https://cdn.discordapp.com/attachments/1022830713164279830/1225005393395322910/image.png?ex=661f8e2d&is=660d192d&hm=2e9d00d210c6fa200b4cae809daac6e07398f76f104572e59270d56bb16f6011&)

Ensuite il faut générer token avec les paramètres par défaut

![alt text](https://cdn.discordapp.com/attachments/1022830713164279830/1225006263113486366/image.png?ex=661f8efc&is=660d19fc&hm=60518384e2524efc75d44b0bb8466b12be87ef2d46c870f00cfe5d7eb6aeac14&)

Il faut surtout bien garder le generate token qui est donné.
On va aller le mettre dans les credentials jenkins à ce path
![alt text](https://cdn.discordapp.com/attachments/1022830713164279830/1225006423432237097/image.png?ex=661f8f22&is=660d1a22&hm=5d5903a8cef9f903bc6ac8f6807d7d286769f81d699e90697698becf492c8f9c&)

Et il faut ajouter un credential de type secret text avec comme secret le token généré par artifactory, et en id, un id qu’on utilisera pour faire référence à ce token dans le jenkins file.

### JenkinsFile
Après cela, il faut que l’on installe les tools nécessaires dans jenkins. (pour ça il faut les plugins associés)
![alt text](https://cdn.discordapp.com/attachments/1022830713164279830/1225007683908669461/image.png?ex=661f904f&is=660d1b4f&hm=b46321985c71b33859ac5e1cb426bbd38ec3280596be2178dd74251a0bbfcc9c&)
On garde les paramètres par défaut.
Après avoir fait cela il faut modeler le jenkins file pour nos besoins de test.


Voici les éléments de bases du jenkins file qui sont, l’utilisation de l’agent jenkins, les tools utilisés et le credential artifactory que l’on a renseigné dans jenkins.
```
pipeline {
   agent {
       label 'agent1'
   }
   tools {
       maven 'Maven 3.9.6'
       jdk 'jdk17'
       jfrog 'jfrog-cli'
   }
   environment {
       ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
   }
}
```


## Problèmes potentiels

Plus d'espace sur la machine hote.
Résolution : supprimer les anciennes branches dans le workspace de l'agent jenkins (l'utilisateur sur la machine hote qui a été créé spécifiquement pour servir d'agent)

Les tests end to end ne passent plus. 
Résolution : le fichier cli/e2e_expected_output n'a pas été modifié après avoir changé le fichier cli/demo.txt

#

# Rapport Devops

Notre travail sur la partie DevOps a eu plusieurs buts:
1. [Avoir une branche principale “main” toujours stable](#avoir-une-branche-principale-main-toujours-stable)
2. [Un Multibranch Pipeline validant les pushs](#un-multibranch-pipeline-validant-les-pushs)
3. [Une sauvegarde de builds valide](#une-sauvegarde-de-builds-valide)

## Avoir une branche principale “main” toujours stable

Lorsque l'on travaille sur un projet, il est crucial d'avoir une branche principale stable pour garantir la livraison d'un code fonctionnel sans cassures inattendues. C'est pourquoi la structure de notre fichier Jenkins est la plus robuste pour la branche principale :

```
stages {
       stage('main') {
           when{
              branch 'main'
           }
           steps {
               sh 'sudo ./build-all.sh'
               sh ‘ sudo docker compose up -d'
               sh 'cd backend && sudo mvn verify'
               sh 'cd cli && sudo mvn clean package'
               dir("backend/target") {
                   sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /java_web-app'
               }
               dir("cli/target"){
                   sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} cli-0.0.1-SNAPSHOT.jar /java_web-app'
               }
                sh 'sudo docker tag team-g-tcf-scheduler-service:latest simonbeurel/team-g-tcf-scheduler-service:latest '
                sh 'sudo docker tag team-g-tcf-bank-service:latest simonbeurel/team-g-tcf-bank-service:latest'
                sh 'sudo docker push simonbeurel/team-g-tcf-scheduler-service:latest'
                sh 'sudo docker push simonbeurel/team-g-tcf-bank-service:latest'
               sh 'sudo docker compose down'
           }
       }
```

Dans ce Jenkinsfile, lorsqu'un push est effectué sur la branche principale, nous procédons à la construction du backend, de la CLI, du scheduler et de la bank, suivie d'un démarrage du service via Docker Compose.

Une fois que tout est en place, nous commençons par vérifier l'intégrité du code en exécutant les tests unitaires et d'intégration du backend avec la commande mvn verify, puis nous effectuons un mvn clean package pour garantir que le code de la cli est compilé à partir de zéro tout en étant sûr que celui-ci est "clean".

Nous effectuons ensuite les tests end to end à partir de la cli. Pour cela nous passons en entrée un fichier contenant les commandes à effectuer et stockons dans un fichier les sorties des commandes. Comme la sortie est réplicable sans avoir d'informations générées (comme des ids, des heures ou autre sortie qui changerait) nous pouvons simplement vérifier une fois le fichier de sortie de ces tests, l’enregistrer comme fichier de sortie attendue faisant fois et le comparer avec le fichier généré lors du test.

Après avoir confirmé la stabilité de notre branche principale, nous téléchargeons les fichiers JAR du backend et de la CLI sur Artifactory, ce qui nous permet de stocker des versions valides prêtes à être déployées si nécessaire.

Ensuite, nous publions les images Docker du scheduler et de la bank sur Docker Hub, ce qui nous permet de les récupérer facilement dans d'autres branches, sachant que ces services externes ne changeront que rarement, voire jamais.

Enfin, pour éviter tout conflit de nom de conteneur, nous terminons en arrêtant les services Docker avec docker compose down. Cette approche garantit un flux de travail stable et cohérent sur la branche principale de notre projet.





## Un Multibranch Pipeline validant les pushs

Au travers de notre travail de DevOps, nous avons mis en place sur Jenkins un multibranch pipeline qui est très utile dans la vérification des push sur des branches multiples. 

Ce pipeline est configuré pour surveiller les modifications sur toutes les branches de notre dépôt Git, ce qui nous permet d'automatiser le processus de construction, de test et de déploiement pour chaque branche.

Ainsi, dès qu'un développeur pousse des modifications sur une branche, le pipeline est déclenché, exécutant les étapes nécessaires pour garantir la qualité du code et la stabilité de l'application. 

Cela nous permet de détecter rapidement les problèmes potentiels et de les corriger avant qu'ils ne deviennent des problèmes majeurs, contribuant ainsi à maintenir un flux de développement fluide et efficace.

Nous avons établi un plan d'exécution de test selon les branches :
autres que develop et main : effectue uniquement les tests unitaires dans l'optique de s'assurer que la fonctionnalité ajoutée fonctionne correctement.
develop : effectue seulement les tests unitaires et d'intégration pour s'assurer que la fonctionnalité ajoutée sur develop s'intègre correctement au projet.
main : effectue tous les tests : unitaires, d'intégration et end to end pour s'assurer que le projet soit stable et livrable.


## Une sauvegarde de builds valide

Comme mentionné précédemment, nous avons adopté une stratégie de stockage des images Docker et des fichiers .jar générés par nos builds sur Artifactory ainsi que sur Docker Hub. Cette approche nous a permis tout d'abord d'explorer et de comparer les deux méthodes pour le stockage de nos artefacts.

Cependant, son avantage principal réside dans la disponibilité rapide et fiable d'images Docker fonctionnelles. Cela nous permet d'économiser un temps précieux lors de la construction du projet, car nous pouvons facilement récupérer des versions stables et prêtes à être déployées à partir de nos référentiels. Cette efficacité renforce notre flux de travail de développement et contribue à la rapidité et à la fiabilité de nos déploiements.

