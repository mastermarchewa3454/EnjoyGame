using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour
{
    public int damage = 5;

    void OnCollisionEnter2D(Collision2D col)
    {

        if (col.gameObject.CompareTag("Enemy") || col.gameObject.CompareTag("Player"))
        {
            col.gameObject.SendMessage("OnDamage", damage);
        }

        Destroy(gameObject);
    }
}
