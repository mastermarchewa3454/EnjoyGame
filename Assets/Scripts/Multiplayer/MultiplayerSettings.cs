using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MultiplayerSettings : MonoBehaviour
{
    public static MultiplayerSettings multiSettings;

    public bool delayStarting;

    public int menuScene;
    public int multiScene;

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
