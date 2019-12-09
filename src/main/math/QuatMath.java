package main.math;

import com.sun.javafx.geom.Quat4f;

import static java.lang.Math.cos;

public class QuatMath {

    //угол в радианах!
    public static Quat4f createQuat(Vector3 rotateV,double angle){
        Quat4f quat4f = new Quat4f();
        rotateV.normalize();
        quat4f.w = (float) cos(angle/2);
        quat4f.x = rotateV.getX();
        quat4f.y = rotateV.getY();
        quat4f.z = rotateV.getZ();
        return quat4f;
    }

    public static Quat4f createQuat(Vector3 v){
        Quat4f quat4f = new Quat4f();
        v.normalize();
        quat4f.w = 0;
        quat4f.x = v.getX();
        quat4f.y = v.getY();
        quat4f.z = v.getZ();
        return quat4f;
    }

    public static Quat4f invertQuat(Quat4f q){
        Quat4f q2 = new Quat4f();
        q2.w = -q.w;
        q2.x = -q.x;
        q2.y = -q.y;
        q2.z = -q.z;
        q2.normalize();
        return q2;
    }

    public static Quat4f mul(Quat4f a,Quat4f b){
        Quat4f result = new Quat4f();
        result.w =a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
        result.x =a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;
        result.y =a.w * b.y - a.x * b.z + a.y * b.w + a.z * b.x;
        result.z =a.w * b.z + a.x * b.y - a.y * b.x + a.z * b.w;
        return result;
    }

    public static Vector3 transformVector(Quat4f q,Vector3 v){
        Quat4f t = new Quat4f();
        t = mul(q,createQuat(v));
        t = mul(t,invertQuat(q));
        return new Vector3(t.x,t.y,t.z);
    }
}
