using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spawner : MonoBehaviour
{
    [SerializeField]
    GameObject archer;

    [SerializeField]
    GameObject mole;

    [SerializeField]
    GameObject treant;

    GameObject player;

    // Start is called before the first frame update
    void Start()
    {
        switch (PlayerPrefs.GetInt("character", 1))
        {
            case 1: player = archer; break;
            case 2: player = mole; break;
            case 3: player = treant; break;
            default: player = archer; break;
        }

    
        player = Instantiate(player, transform.position, Quaternion.identity);
        player.name = "Player";
    }

}
