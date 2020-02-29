using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour
{
    public int damage = 5;

    void OnCollisionEnter2D(Collision2D col)
    {
        Destroy(gameObject);

        if (col.gameObject.CompareTag("Entity"))
        {
            col.gameObject.SendMessage("OnDamage", damage);
        }
    }
}
