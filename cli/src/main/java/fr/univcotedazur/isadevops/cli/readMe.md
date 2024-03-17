# Organisation de ce dossier : 

Ce dossier comporte deux sous dossiers : commands et model
## Dossier commands : 
Dossier qui contient les différentes actions qui pourront être demandées par les utilisateurs du projet 

Par exemple : 
```java
@ShellMethod("Register a customer in the CoD backend (register CUSTOMER_NAME CREDIT_CARD_NUMBER)")
    public CliCustomer register(String name, String creditCard) {
        CliCustomer res = restTemplate.postForObject(BASE_URI, new CliCustomer(name, creditCard), CliCustomer.class);
        cliContext.getCustomers().put(Objects.requireNonNull(res).getName(), res);
        return res;
    }
```

## Dossier model :
Ce dossier comporte le squelette des différentes entités que l'on a défini dans notre diagramme de classe sur le rapport de début de projet. 

Par exemple :
Voir fichier CliCustomer.java