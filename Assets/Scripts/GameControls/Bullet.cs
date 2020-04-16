using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Script for bullet objects
/// </summary>
public class Bullet : MonoBehaviour
{
    [SerializeField]
    private GameObject hitEffect;
    [SerializeField]
    private int damage = 10;

    void Start()
    {
        if (this.name == "Lazer2(Clone)")
        {
            damage = PlayerPrefs.GetInt("attack", damage);
        }
    }

    /// <summary>
    /// Handles collisions of bullets
    /// </summary>
    /// <param name="col">Collision2D object</param>
    void OnCollisionEnter2D(Collision2D col)
    {
        // Plays animation if available
        if (hitEffect != null)
        {
            GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);
            Destroy(effect, 0.1f);
        }

        // Damages player / enemy
        if (col.gameObject.CompareTag("Enemy") || col.gameObject.CompareTag("Player"))
        {
            col.gameObject.SendMessage("OnDamage", damage);
        }

        // Destroys the bullet
        Destroy(gameObject);
    }
}
