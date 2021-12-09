package ru.larionov.pool.impl;


import ru.larionov.pool.SpritesPool;
import ru.larionov.sprite.impl.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
