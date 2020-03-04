using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class Health : NetworkBehaviour
{
    [SerializeField]
    private int totalHealth = 100;

    [SyncVar]
    int currHealth;
    Transform bar;

    // Start is called before the first frame update
    void Start()
    {
        currHealth = totalHealth;
        bar = transform.Find("HealthBar/Bar");
    }

    [ClientRpc]
    void RpcSetSize(float sizeNormalized)
    {
        bar.localScale = new Vector2(sizeNormalized, 1f);
    }

    void OnDamage(int damage)
    {
        if (this.isServer)
        {
            currHealth -= damage;

            if (currHealth <= 0)
            {
                Destroy(gameObject);
            }

            RpcSetSize((float)currHealth / totalHealth);
        }
    }
}
