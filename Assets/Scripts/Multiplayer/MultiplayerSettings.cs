using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MultiplayerSettings : MonoBehaviour
{
    public static MultiplayerSettings multiSettings;

    [SerializeField] public bool delayStarting;
    public int maxPlayers;
    public string menuScene;
    public string multiScene;

    private void Awake()
    {
        if(MultiplayerSettings.multiSettings == null)
        {
            MultiplayerSettings.multiSettings = this;
        }
        else
        {
            if(MultiplayerSettings.multiSettings != this)
            {
                Destroy(this.gameObject);
            }
        }
        DontDestroyOnLoad(this.gameObject);
    }
}
