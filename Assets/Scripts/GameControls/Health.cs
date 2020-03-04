using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Health : MonoBehaviour
{
    public int totalHealth = 100;

    int currHealth;
    Transform bar;

    // Start is called before the first frame update
    void Start()
    {
        currHealth = totalHealth;
        bar = transform.Find("HealthBar/Bar");
    }

    void SetSize(float sizeNormalized)
    {
        bar.localScale = new Vector2(sizeNormalized, 1f);
    }

    void OnDamage(int damage)
    {
        currHealth -= damage;

        if (currHealth <= 0)
        {
            Destroy(gameObject);
        }

        SetSize((float)currHealth / totalHealth);
    }
}
