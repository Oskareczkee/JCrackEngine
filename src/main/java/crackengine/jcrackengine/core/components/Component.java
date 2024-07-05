package crackengine.jcrackengine.core.components;

import crackengine.jcrackengine.core.GameObject;

public abstract class Component {
    protected GameObject entity; //game object component is binded to
    public void bind(GameObject entity) {
        this.entity = entity;
    }

    public GameObject getEntity() {
        return entity;
    }
}
