/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import main.math.Vector3;
import main.third.IModel;
import main.third.Material;
import main.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Parallelepiped implements IModel {
    private Vector3 LTF, RBN;
    private Material m;

    /**
     * Создаёт экземпляр параллелипипеда
     * @param LTF Левая Верхняя Дальняя точка (Left Top Far)
     * @param RBN Правая Нижняя Ближняя точка (Right Bottom Near)
     */
    public Parallelepiped(Vector3 LTF, Vector3 RBN) {
        this.LTF = LTF;
        this.RBN = RBN;
    }

    public Parallelepiped(Vector3 vector3, Vector3 vector31, Material material) {
        this.LTF = vector3;
        this.RBN = vector31;
        this.m = material;
    }


    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        /*Дальняя сторона (Z фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())
                }), true));
        /*Ближняя сторона  (Z фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), RBN.getZ())
                }), true));
        
        /*Верхняя сторона (Y фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                    new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())
                }), true));
        /*Нижняя сторона (Y фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), LTF.getZ())
                }), true));
        
        /*Левая сторона (X фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                    new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(LTF.getX(), RBN.getY(), LTF.getZ())
                }), true));
        /*Правая сторона (X фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                    new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                    new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                    new Vector3(RBN.getX(), RBN.getY(), LTF.getZ())
                }), true));
        
        return lines;
    }


}
