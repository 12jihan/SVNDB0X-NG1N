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

This is all for loading in one object.
You can definitely use this method to load in multiple object at the same time as well.
