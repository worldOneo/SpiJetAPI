![logo](https://raw.githubusercontent.com/worldOneo/SpiJetAPI/master/resources/Logo.png)
# SpiJetAPI
An API Which contains all the small things I use in my Projects

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

//Query
CachedRowSet crs = sqb.executeQuery(hds);
```

## Particles
Particles are supported in multiple versions where the particle is either `Particle.` or `Effect.`.
```Java
//Gets you the right wrapper for your version
ParticleWrapper particleWrapper = ParticleUtils.getWrapper();

//Create 100 particles at the location of the player in the shape of a circle with the radius of 2 for all players
particleWrapper.createSpherical(Particle.REDSTONE, player.getLocation, 100, 2, 0, 2, Bukkit.getOnlinePlayers());
```

## GuiAPI
This is just a copy of my GuiApi for more information read: [Gui API](https://github.com/worldOneo/GUI-API/wiki)

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
