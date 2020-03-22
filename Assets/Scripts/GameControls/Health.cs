using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Health of entities
/// </summary>
public class Health : MonoBehaviour
{
    [SerializeField]
    private int totalHealth = 100;
    int currHealth;
    Transform bar;
    SceneChanger sceneChanger;
    GameHUD gameHUD;

    /// <summary>
    /// Gets the health bars of entities
    /// </summary>
    void Start()
    {
        if (gameObject.tag == "Player")
            currHealth = PlayerPrefs.GetInt("health", totalHealth);
        else
            currHealth = totalHealth;
        bar = transform.Find("HealthBar/Bar");
        UpdateBar();

        sceneChanger = FindObjectOfType<SceneChanger>();
        gameHUD = FindObjectOfType<GameHUD>();
    }

    /// <summary>
    /// Updates the health bar
    /// </summary>
    void UpdateBar()
    {
        float sizeNormalized = (float) currHealth / totalHealth;
        bar.localScale = new Vector2(sizeNormalized, 1f);
    }

    /// <summary>
    /// Deals a certain amount of damage to the entity
    /// </summary>
    /// <param name="damage">Amount of damage dealt</param>
    void OnDamage(int damage)
    {
        SetCurrHealth(currHealth - damage);

        if (currHealth <= 0)
        {
            if (gameObject.tag == "Player")
            {
                sceneChanger.ChangeToEndScene();
            }
            else
            {
                Destroy(gameObject);
            }
        }
    }

    /// <summary>
    /// Gets the current health of entity
    /// </summary>
    /// <returns></returns>
    public int GetCurrHealth()
    {
        return currHealth;
    }

    /// <summary>
    /// Sets the current health of entity
    /// </summary>
    /// <param name="health">New health</param>
    public void SetCurrHealth(int health)
    {
        currHealth = health;
        UpdateBar();

        if (gameObject.tag == "Player")
        {
            gameHUD.UpdateHealth(currHealth);
        }
    }

    /// <summary>
    /// Gets the max health of entity
    /// </summary>
    /// <returns></returns>
    public int GetMaxHealth()
    {
        return totalHealth;
    }
}
