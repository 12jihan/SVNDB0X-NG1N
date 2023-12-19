# SVNDB0X-NG1N (Sandbox-engine)
A game engine for running simulation tests
still in extra early state with no goals for full release

## How to use:
Currently still working out a way for this tool to be a bit more intuitive:
```java
// Example of loading in a custom model
Model yourModel = ModelLoader.loadModel("model-id",
  "/Path/to/your/model/object/file.obj",
  scene.getTextureCache());
scene.addModel(yourModel);
        
yourEntity = new Entity("Name", yourModel.getId());
terraEntity.setPosition(0f, 0f, -5.0f);
terraEntity.updateModelMatrix();
scene.addEntity(yourEntity);

```

### Disclaimer:
This is all for loading in one object.
Using other custom methods, you can use this method to load multiple of the same object simultaneously.

Please don't ask for any bug fixes unless you have any advice on how I could make this a bit better.
This is purely for learning purposes.
