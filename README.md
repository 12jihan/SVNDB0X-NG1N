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
## Currently Broken:
- ImGui is currently not working correctly when I load certain instances.
- Lights still need to be fully fleshed out.
- Skybox only works with the sample Skybox model from the tutorial.
- I still need to implement animations fully.
- Perlin noise with terrain generation isn't functioning at the moment.
- I'm still determining the current state of fog generation.
- No audio at this time.
- Shadows do not exist (primarily because of the lighting situation)
- Debugging is non-existent, but I am currently working on parsing out common errors that would be useful.

### Disclaimer:
This is all for loading in one object.
Using other custom methods, you can use this method to load multiple of the same object simultaneously.

Please don't ask for any bug fixes.
I would appreciate any advice on how I could improve this.
This is purely for learning purposes.
