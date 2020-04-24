using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Script for handling multiplayer atributes.
/// </summary>
public class MultiplayerSettings : MonoBehaviour
{
    public static MultiplayerSettings multiSettings;

    [SerializeField] public bool delayStarting;
    public int maxPlayers;
    public string menuScene;
    public string multiScene;

    /// <summary>
    /// Keep this class open in the game.
    /// </summary>
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
