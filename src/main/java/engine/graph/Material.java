package engine.graph;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;

public class Material {

    private List<Mesh> meshList;
    private Vector4f ambientColor;
    private String texturePath;
    private String normalMapPath;
    private float reflectance;
    private Vector4f specularColor;
    private Vector4f diffuseColor;
    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    public Material() {
        diffuseColor = DEFAULT_COLOR;
        ambientColor = DEFAULT_COLOR;
        meshList = new ArrayList<>();
    }

    public void cleanup() {
        meshList.forEach(Mesh::cleanup);
    }

    /*
     * Mesh List is the list of meshes that use this material.
     */
    public List<Mesh> getMeshList() {
        return meshList;
    }

    /*
     * Texture Path is the path to the texture file.
     */
    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getTexturePath() {
        return texturePath;
    }

    /*
     * Ambient Color is the color of the light reflected from the object.
     */
    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    /*
     * Reflectance is the amount of light reflected by the object.
     */
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public float getReflectance() {
        return reflectance;
    }

    /*
     * Specular Color is the color of the light reflected from the object.
     */
    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }

    /*
     * Diffuse Color is the color of the object when lit by a light source.
     */
    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    /*
     * Normal Map is a texture that stores a normal at each pixel.
     */
     public String getNormalMapPath() {
        return normalMapPath;
    }
    
    public void setNormalMapPath(String normalMapPath) {
        this.normalMapPath = normalMapPath;
    }

}
