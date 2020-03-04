using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class Bullet : NetworkBehaviour
{
    public int damage = 5;

    void OnCollisionEnter2D(Collision2D col)
    {
        /*
        if (col.gameObject.CompareTag("Enemy") || col.gameObject.CompareTag("Player"))
        {
            col.gameObject.SendMessage("OnDamage", damage);
        }*/

        Destroy(gameObject);
    }
}
