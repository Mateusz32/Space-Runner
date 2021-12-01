package model;

public enum SOUNDS {
    SHIP_COLLIDED_WITH_STAR_SOUND("file:src/main/resources/ship_collided_with_star_sound.mp3"),
    SHOOT_LASER_SOUND("file:src/main/resources/shoot_laser_sound.mp3"),
    METEOR_COLLIDE_WITH_SHIP_SOUND("file:src/main/resources/meteor_collide_with_ship_sound.mp3");

    private String sound;

    SOUNDS(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return this.sound;
    }
}
