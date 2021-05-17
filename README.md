![logo](https://raw.githubusercontent.com/worldOneo/SpiJetAPI/master/resources/Logo.png)

[![](https://jitpack.io/v/worldOneo/SpiJetAPI.svg)](https://jitpack.io/#worldOneo/SpiJetAPI)

# SpiJetAPI - (1.8.8 - 1.16)

An API Which contains all the small things I use in my Projects.
It adds lots of abstraction to the plain Spigot API resulting in rappid development.

# Examples:

## SQL

```Java
//SQL connections
DataSourceBuilder dsb = new DataSourceBuilder();
dsb.setUsername("Simpsons")
        .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/simpsons")
        .setPassword("S1mp50ns!");

HikariDataSource hds = dsb.build();

//Query Something
SQLQueryBuilder sqb = new SQLQueryBuilder("SELECT * FROM simpsons WHERE name=? AND age=?;");
sqb.setParameter(1, "Homer");
sqb.setParameter(2, 42);

//Query sync
CachedRowSet crs = sqb.executeQuery(hds);
//Query async
CompletableFuture<CachedRowSet> future = sqb.executeQueryAsync(hds);
```

## Particles

Particles are supported in multiple versions where the particle is either `Particle.` or `Effect.`.

```Java
//Gets you the right wrapper for your version
ParticleWrapper particleWrapper = ParticleUtils.getWrapper();

//Create 100 particles at the location of the player in the shape of a sphere with the radius of 2 for all players
particleWrapper.createSpherical(Particle.REDSTONE, player.getLocation, 100, 2, 2, 2);
```

## GUI-API

The "normal" ways to create GUIs is often based on slot and/or string comparison of the item used as a button and the
InventoryClickEvent. This GUI-API aims to connect the events and the creation of the API to create GUIs easier.

```java
GUI gui = new GUI();
gui
 .setSize(27) //3 rows
 .setGUITitle("My First GUI!");
Button button = new Button(this::action); //This function (this::action) is called on click
button
 .setSlot(13) //Define the position of the button
 .setMaterial(Material.PAPER) //Define the material
 .setTitle("Click me!"); //Set the name
gui.addWidget(button); //Add the button to the gui
InventoryGUIManager.getInstance().open(gui, player); //open the gui as inventory

```

That's all no need to check stuff. No need to register listeners.

## Configurations

The configuration utility of this plugin support JSON and YAML configs

```Java
File ymlConfig = new File(getDataFolder(), "config.yml");
File jsonConfig = new File(getDataFolder(), "config.json");

//load data
//Get yml config write default if not exist
YamlConfiguration yamlConfiguration = ConfigUtils.load(ymlConfig, getResource("config.yml"));

//Get json config write default if not exist (Can be any kind of data class) 
MyOptionClass myOptionClass = ConfigUtils.loadJson(jsonConfig, MyOptionClass.class, new MyOptionClass());

//Save data
//Save yml data
yamlConfiguration.save(ymlConfig);

//Save json data
ConfigUtils.saveJson(jsonConfig, myOptionClass);
```
