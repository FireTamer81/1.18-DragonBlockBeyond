package io.firetamer.dragonblockbeyond.util;

import com.mojang.math.Vector3d;
import net.minecraft.util.Mth;

public class DBVector3d extends Vector3d {
    public static final DBVector3d ZERO = new DBVector3d(0.0D, 0.0D, 0.0D);

    public DBVector3d(double pX, double pY, double pZ) {
        super(pX, pY, pZ);
    }




    /**
     * Normalizes the vector to a length of 1 (except if it is the zero vector)
     */
    public DBVector3d normalize() {
        double d0 = Mth.sqrt((float) (this.x * this.x + this.y * this.y + this.z * this.z));
        return d0 < 1.0E-4D ? ZERO : new DBVector3d(this.x / d0, this.y / d0, this.z / d0);
    }

    /**
     * Returns a new vector with the result of this vector x the specified vector.
     */
    public DBVector3d cross(Vector3d pVec) {
        return new DBVector3d(this.y * pVec.z - this.z * pVec.y, this.z * pVec.x - this.x * pVec.z, this.x * pVec.y - this.y * pVec.x);
    }

    public DBVector3d subtract(DBVector3d pVec) {
        return this.subtract(pVec.x, pVec.y, pVec.z);
    }

    public DBVector3d subtract(double pX, double pY, double pZ) {
        return this.add(-pX, -pY, -pZ);
    }

    public DBVector3d add(DBVector3d pVec) {
        return this.add(pVec.x, pVec.y, pVec.z);
    }

    /**
     * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
     * vector.
     */
    public DBVector3d add(double pX, double pY, double pZ) {
        return new DBVector3d(this.x + pX, this.y + pY, this.z + pZ);
    }

    public DBVector3d reverse() {
        return this.scaleDouble(-1.0D);
    }

    public DBVector3d scaleDouble(double pFactor) {
        return this.multiply(pFactor, pFactor, pFactor);
    }

    public DBVector3d multiply(DBVector3d pVec) {
        return this.multiply(pVec.x, pVec.y, pVec.z);
    }

    public DBVector3d multiply(double pFactorX, double pFactorY, double pFactorZ) {
        return new DBVector3d(this.x * pFactorX, this.y * pFactorY, this.z * pFactorZ);
    }
}
