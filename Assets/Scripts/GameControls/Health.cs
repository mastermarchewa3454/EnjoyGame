using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Health : MonoBehaviour
{
    [SerializeField]
    private int totalHealth = 100;
    int currHealth;
    Transform bar;
    SceneChanger sceneChanger;
    GameHUD gameHUD;

    // Start is called before the first frame update
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
            if (gameObject.tag == "Player")
            {
                sceneChanger.ChangeToStartScene();
            }
            else
            {
                Destroy(gameObject);
            }
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

        if (gameObject.tag == "Player")
        {
            gameHUD.UpdateHealth(currHealth);
        }
    }

    public int GetMaxHealth()
    {
        return totalHealth;
    }
}
