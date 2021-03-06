package org.openrndr.math

import java.io.Serializable

data class Vector3(val x: Double, val y: Double, val z: Double) : Serializable {

    companion object {
        val ZERO = Vector3(0.0, 0.0, 0.0)
        val UNIT_X = Vector3(1.0, 0.0, 0.0)
        val UNIT_Y = Vector3(0.0, 1.0, 0.0)
        val UNIT_Z = Vector3(0.0, 0.0, 1.0)

        fun fromSpherical(s: Spherical): Vector3 {
            val sinPhiRadius = Math.sin(s.phi) * s.radius
            return Vector3(
                    sinPhiRadius * Math.sin(s.theta),
                    Math.cos(s.phi) * s.radius,
                    sinPhiRadius * Math.cos(s.theta))
        }
    }

    val xyz0 get() = Vector4(x, y, z, 0.0)
    val xyz1 get() = Vector4(x, y, z, 1.0)

    val xy get() = Vector2(x, y)
    val yx get() = Vector2(y, x)
    val zx get() = Vector2(z, x)
    val xz get() = Vector2(x, z)

    val normalized: Vector3
        get() {
            val l = 1.0 / length
            if (l.isNaN() || l.isInfinite()) {
                return ZERO
            }
            return this * l
        }

    infix fun reflect(surfaceNormal: Vector3) = this - surfaceNormal * (this dot surfaceNormal) * 2.0

    private operator fun get(i: Int): Double {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw RuntimeException("unsupported index")
        }
    }

    operator fun unaryMinus() = Vector3(-x, -y, -z)
    operator fun plus(v: Vector3) = Vector3(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector3) = Vector3(x - v.x, y - v.y, z - v.z)
    operator fun times(v: Vector3) = Vector3(x * v.x, y * v.y, z * v.z)
    operator fun times(s: Double) = Vector3(x * s, y * s, z * s)
    operator fun div(s: Double) = Vector3(x / s, y / s, z / s)

    infix fun dot(v: Vector3) = x * v.x + y * v.y + z * v.z

    infix fun cross(v: Vector3) = Vector3(
            y * v.z - z * v.y,
            -(x * v.z - z * v.x),
            x * v.y - y * v.x)

    infix fun projectedOn(v: Vector3) = (this dot v) / (v dot v) * v
    val length: Double get() = Math.sqrt(x * x + y * y + z * z)
    fun toDoubleArray() = doubleArrayOf(x, y, z)
}

operator fun Double.times(v: Vector3) = v * this

fun min(a: Vector3, b: Vector3) = Vector3(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z))
fun max(a: Vector3, b: Vector3) = Vector3(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z))
