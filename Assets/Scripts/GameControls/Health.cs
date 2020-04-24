
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Photon.Pun;

/// <summary>
/// Health of entities
/// </summary>
public class Health : MonoBehaviourPunCallbacks, IPunObservable
{
    [SerializeField]
    private int totalHealth = 100;
    [SerializeField]
    int currHealth;
    Transform bar;
    SceneChanger sceneChanger;
    GameHUD gameHUD;

    public static bool isDuoMode = false;
    private PhotonView pV;

    /// <summary>
    /// Gets the health bars of entities
    /// </summary>
    void Start()
    {
        sceneChanger = FindObjectOfType<SceneChanger>();
        if (isDuoMode)
        {
            pV = GetComponent<PhotonView>();
            if(gameObject.tag == "Player")
            {
                if(MultiplayerSettings.multiSettings.multiScene != "Level 1")
                {
                    if (gameObject.name == "Player")
                    {
                        currHealth = PhotonRoom.theRoom.playerHealth1;
                    }
                    else
                    {
                        currHealth = PhotonRoom.theRoom.playerHealth2;
                    }
                }
                else
                {
                    currHealth = PlayerPrefs.GetInt("health", totalHealth);
                }
            }

            else
            {
                currHealth = totalHealth;
            }
        }
        if(!isDuoMode)
        {
            if (gameObject.tag == "Player")
                currHealth = PlayerPrefs.GetInt("health", totalHealth);
            else
                currHealth = totalHealth;
        }
        
        bar = transform.Find("HealthBar/Bar");
        UpdateBar();

        
        gameHUD = FindObjectOfType<GameHUD>();
        
    }

    /// <summary>
    /// Sets health for duo mode
    /// </summary>
    /// <param name="stream"></param>
    /// <param name="info"></param>
    public void OnPhotonSerializeView(PhotonStream stream, PhotonMessageInfo info)
    {
        if (stream.IsWriting)
        {
            stream.SendNext(currHealth);
            Debug.Log("Send current health value");
        }
        else if (stream.IsReading)
        {
            currHealth = (int)stream.ReceiveNext();
            Debug.Log("Receive current health value");
        }
    }

    /// <summary>
    /// Updates the health bar
    /// </summary>
    void UpdateBar()
    {
        float sizeNormalized = (float)currHealth / totalHealth;
        bar.localScale = new Vector2(sizeNormalized, 1f);
    }
    private void Update()
    {
        if (isDuoMode)
        {
            UpdateBar();
        }                     
    }
    [PunRPC]
    private void isDead()
    {   
       Destroy(gameObject);                            
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
                if(isDuoMode)
                {
                    pV.RPC("isDead", RpcTarget.All);
                }
                else
                {
                    Destroy(gameObject);
                }                                            
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
