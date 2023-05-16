
# Consideracions:
> - Com vam parlar implementem només les classes DAO de les taules que es poden utilitzar al menú.
> - Pels candidats, hem modificat la constraint d'FK perquè quan eliminem una persona s'elimini el candidat relacionat. En canvi, no es permet eliminar una comunitat autònoma sense que aquesta estigui buida de províncies. En aquest cas, saltarà un missatge d'error SQL de la base de dades per informar a l'usuari de que no es pot eliminar la comunitat autònoma.
> - També hem modificat les FK perquè quan actualitzem els registres s'actualitzin en cascada a la resta de taules.
> - Qualsevol excepció SQL emesa per la BD es captura i es mostra printa per pantalla per informar a l'usuari.


# Projecte programació DAO
Aquest projecte pretent a través d'un menú per consola, accedir i efectuar operacions CRUD a una base de dades de les eleccions gnerals. Només estan implementades les accions sobre les taules _persones_, _candidatures_ i _comunitats_autonomes_. El nostre projecte està estructurat en model, vista i controlador.
## Model
El model està format per les classes que representen les taules de la base de dades. Aquestes classes són:
- ComAutonoma
- Provincia
- Municipi
- Persona
- Candidat
- Candidatura

Cada classe té els seus atributs inicialitzats a _null_ pels camps que poden ser _null_, els seus getters i setters i els seus constructors (de tots els camps, només de l'id i de tots els camps excepte l'id).
A més, cada classe té un mètode _toString()_ que retorna un String amb els valors dels atributs de la classe.

Quan a la taula de la base de dades l'**id** és un INT UNSIGNED, al nostre model és un _long_, ja que al ser _unsigned_ ocupa més que l'espai ordinari d'un int. 
De la mateixa manera, quan l'**id** és un TINYINT UNSIGNED, al nostre model serà un _int_. 

Quan és una dada primitiva i a la base de dades és *null*, en java obtenim el valor 0, així que hauria de comprovar-se si el valor que obtenim de la base de dades és null o no. Però en aquest cas, no hem hagut de comprovar-ho, ja que totes les dades primitives de la base de dades són _not null_, així que sempre ens tornaran el valor real.

## Vista
Aquí només trobarem la classe **Print**, que conté tots els mètodes per imprimir informació per pantalla.

## Controlador
Al controllador trobem: 

- La classe **Main**, que inicia l'aplicatiu.
- La classe **Controller**, que conté tots els processos comuns i execucions dels menús comuns.
- La classe **DataValidator**, que conté les validacions de dades comuns (si és un CHAR(6), un VARCHAR(50), un DATE...).
- La classe **DataConverter**, que conté aquells mètodes per convertir dades (de String a Date, de Date a String...).
- La classe **Missatges**, que conté aquells missatges genèrics que cal mostrar a l'usuari.
- El package **DAO**, que conté les interfícies DAO i les seves implementacions.
- El package **persona**, que conté el menú i les accions sobre la taula persona.
- El package **candidatura**, que conté el menú i les accions sobre la taula candidatura.
- El package **comunitat_autonoma**, que conté el menú i les accions sobre la taula comunitat_autonoma.

### DAO
Dins trobem la interfície **DAO**, on s'especifiquen els métodes que han de tenir les classes DAO i el package **mySQL**, on s'allotgen les classesDAO implementades (_PersonaDAO_, _CandidaturaDAO_, _ComAutonoma_), i el gestor de connexions **DBMySQLManager**, amb els mètodes per gestionar les conexions i automatitzar les peticions de lectura i escriptura de la base de dades.

Aquests mètodes són:
- **getConnection()**: retorna una connexió a la base de dades.
- **closeConnection()**: tanca la connexió a la base de dades.
- **read(String, Object...): List<Object[]>** : Se li passa la query i els paràmetres d'aquesta. Detecta automàticament el tipus de paràmetre, crea el PreparedStatement i l'executa. Retorna una llista d'arrays d'objectes, on cada array d'objectes és un registre, i cada objecte de l'array és un camp de la taula.
- **write(String, Object...): int**: Se li passa la query i els paràmetres d'aquesta. Detecta automàticament el tipus de paràmetre, crea el PreparedStatement i l'executa. Retorna el nombre de registres afectats.

Cada classe DAO implementa la interfície DAO i té els mètodes CRUD de la taula que representa. Aquests mètodes són:
- **create(T): boolean**: Crea un registre a la base de dades amb els valors dels atributs de la classe.
- **read(T): boolean**: Llegeix un registre de la base de dades i els assigna als atributs de la classe.
- **readBy(Object): T**: Crea un objecte de la classe amb els atributs trobats per aquella persona a través de l'id passat per paràmetres.
- **update(T): boolean**: Actualitza un registre de la base de dades amb els valors dels atributs de la classe.
- **update(T, String...): boolean**: Actualitza un registre de la base de dades amb els valors dels atributs de la classe només dels atributs especificats.
- **delete(T): boolean**: Elimina un registre de la base de dades.
- **search(String, Object): List<T>**: Retorna els registres que coincideixen amb el valor del camp passat per paràmetres.
- **exists(T): boolean**: Comprova si existeix un registre amb els atributs de l'objecte de la classe a la base de dades.
- **count(): long**: Retorna el nombre de registres de la taula.
- **all(): List<T>**: Retorna tots els registres de la taula.

### Package persona, candidatura i comunitat_autonoma
Contenen:
- El controllador específic de cada taula. Aquest controllador té el **menú**, els mètodes **auxiliars** i les **accions** sobre la taula.
- La classe per **validar** els camps concrets de la taula.
- Els **missatges** específics per les accions d'aquella taula.
