using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Health : MonoBehaviour
{
    [SerializeField]
    private int totalHealth = 100;
    int currHealth;
    Transform bar;

    // Start is called before the first frame update
    void Start()
    {
        if (gameObject.tag == "Player")
            currHealth = PlayerPrefs.GetInt("health", totalHealth);
        else
            currHealth = totalHealth;
        bar = transform.Find("HealthBar/Bar");
        UpdateBar();
    }

    void UpdateBar()
    {
        float sizeNormalized = (float) currHealth / totalHealth;
        bar.localScale = new Vector2(sizeNormalized, 1f);
    }

    void OnDamage(int damage)
    {
        SetCurrHealth(currHealth - damage);

        if (currHealth <= 0)
        {
            Destroy(gameObject);
        }
    }

    public int GetCurrHealth()
    {
        return currHealth;
    }

    public void SetCurrHealth(int health)
    {
        currHealth = health;
        UpdateBar();
    }

    public int GetMaxHealth()
    {
        return totalHealth;
    }
}
