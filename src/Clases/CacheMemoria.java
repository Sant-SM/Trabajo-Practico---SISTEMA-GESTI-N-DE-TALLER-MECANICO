package Clases;

import java.util.HashMap;

public class CacheMemoria<K, T> {

    private HashMap<K, T> cache;

    public CacheMemoria() {
        this.cache = new HashMap<>();
    }

    public void agregar(K clave, T elemento) {
        cache.put(clave, elemento);
    }

    public T obtener(K clave) {
        return cache.get(clave);
    }

    public void eliminar(K clave) {
        cache.remove(clave);
    }

    public boolean existe(K clave) {
        return cache.containsKey(clave);
    }
}

