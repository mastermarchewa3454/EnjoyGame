using Photon.Pun;
using Photon.Realtime;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

/// <summary>
/// Script for handling room atributes.
/// </summary>
public class PhotonRoom : MonoBehaviourPunCallbacks, IInRoomCallbacks
{
    // the room
    public static PhotonRoom theRoom;
    private PhotonView pV;
    public bool isGameLoad;
    public string currentScene;
    public TextMeshProUGUI timerHost;
    public TextMeshProUGUI timerPlayer;
    // info about players
    Player[] photonPlayers;
    public int playersInRoom;
    // start the time

    private bool readyToCount;
    private bool isItStart;
    private float atMaxPlayers;
    public float startTime;
    private float timeToStart;
    private int playersInGame;

    // player health value
    public int playerHealth1;
    public int playerHealth2;

    /// <summary>
    /// Keep this class open in the game.
    /// </summary>
    private void Awake()
    {
        if(PhotonRoom.theRoom == null)
        {
            PhotonRoom.theRoom = this;
        }
        else
        {
            if(PhotonRoom.theRoom != this)
            {
                Destroy(PhotonRoom.theRoom.gameObject);
                PhotonRoom.theRoom = this;
            }
        }
        DontDestroyOnLoad(this.gameObject);
    }

    /// <summary>
    /// Enable loading the scene.
    /// </summary>
    public override void OnEnable()
    {
        base.OnEnable();
        PhotonNetwork.AddCallbackTarget(this);
        SceneManager.sceneLoaded += OnSceneFinishedLoad;

    }

    /// <summary>
    /// Disable loading the scene.
    /// </summary>
    public override void OnDisable()
    {
        base.OnDisable();
        PhotonNetwork.RemoveCallbackTarget(this);
        SceneManager.sceneLoaded -= OnSceneFinishedLoad;
    }

    /// <summary>
    /// Initialize player when scene finishing loading.
    /// </summary>
    /// <param name="scene"></param>
    /// <param name="mode"></param>
    void OnSceneFinishedLoad(Scene scene, LoadSceneMode mode)
    {
        currentScene = scene.name;
        Debug.Log("This is current scene: " + currentScene);
        Debug.Log("This is scene.name " + scene.name);
        if(currentScene == MultiplayerSettings.multiSettings.multiScene)
        {
            isGameLoad = true;
            if(MultiplayerSettings.multiSettings.delayStarting)
            {
                pV.RPC("RPC_LoadedGameScene", RpcTarget.MasterClient);
            }
            else
            {
                RPC_CreatePlayer();
            }
        }
    }

    /// <summary>
    /// Load the players in the PhotonNetwork.
    /// </summary>
    [PunRPC]
    private void RPC_LoadedGameScene()
    {
        playersInGame++;
        Debug.Log("number of players in game " + playersInGame);
        if(playersInGame == PhotonNetwork.PlayerList.Length)
        {
            pV.RPC("RPC_CreatePlayer", RpcTarget.All);
        }
                           
    }

    /// <summary>
    /// Create the player in the PhotonNetwork.
    /// </summary>
    [PunRPC]
    private void RPC_CreatePlayer()
    {
        PhotonNetwork.Instantiate(Path.Combine("ForMulti","PhotonNetworkPlayer"), transform.position, Quaternion.identity,0);
        playersInGame = playersInGame - 2;
    }

    /// <summary>
    /// Set the parameters for the game.
    /// </summary>
    void Start()
    {
        pV = GetComponent<PhotonView>();
        readyToCount = false;
        isItStart = false;
        timeToStart = startTime;
        atMaxPlayers = startTime;
        timerHost.SetText("");
        timerPlayer.SetText("");
    }

    /// <summary>
    /// Check if the countdown can start. Restart the time if user leave the room.
    /// </summary>
    void Update()
    {
        if(MultiplayerSettings.multiSettings.delayStarting)
        {
            if(playersInRoom == 1)
            {
                RestartTime();
                
            }
            if(!isGameLoad)
            {
                if(isItStart && playersInRoom == MultiplayerSettings.multiSettings.maxPlayers)
                {
                    atMaxPlayers -= Time.deltaTime;
                    timeToStart = atMaxPlayers;
                    int t = (int)timeToStart;
                    timerHost.SetText(t.ToString());
                    timerPlayer.SetText(t.ToString());
                }               
                if(timeToStart <= 0)
                {
                    StartGame();
                }
                if(playersInRoom != 2)
                {
                    RestartTime();
                }
            }
        }
        else
        {
            if(isItStart && playersInRoom == MultiplayerSettings.multiSettings.maxPlayers)
            {
                StartGame();
            }
        }
    }

    /// <summary>
    /// Check if the user join the room (for joining lobby).
    /// </summary>
    public override void OnJoinedRoom()
    {
        base.OnJoinedRoom();
        photonPlayers = PhotonNetwork.PlayerList;
        playersInRoom = photonPlayers.Length;
        if(MultiplayerSettings.multiSettings.delayStarting)
        {
            if (playersInRoom == MultiplayerSettings.multiSettings.maxPlayers)
            {
                isItStart = true;
                if (!PhotonNetwork.IsMasterClient)
                     return;              
                PhotonNetwork.CurrentRoom.IsOpen = false;              
            }
        }
        else
        {
            StartGame();
        }
    }

    /// <summary>
    /// Check if other player join the room (for creating lobby).
    /// </summary>
    /// <param name="newPlayer"></param>
    public override void OnPlayerEnteredRoom(Player newPlayer)
    {
        base.OnPlayerEnteredRoom(newPlayer);
        Debug.Log("New player has joined the room");
        photonPlayers = PhotonNetwork.PlayerList;
        playersInRoom++;
        if (MultiplayerSettings.multiSettings.delayStarting)
        {
            if (playersInRoom > 1)
            {
                readyToCount = true;
            }
            if (playersInRoom == MultiplayerSettings.multiSettings.maxPlayers)
            {
                isItStart = true;
                if (!PhotonNetwork.IsMasterClient)
                {
                    return;
                }
                else
                {
                    PhotonNetwork.CurrentRoom.IsOpen = false;
                }
            }
        }
    }

    /// <summary>
    /// Start the game.
    /// Set parameters to duo mode.
    /// Load the level.
    /// </summary>
    void StartGame()
    {
        setToDuoMode();
        isGameLoad = true;
        if (!PhotonNetwork.IsMasterClient)
        {
            return;
        }
        if(MultiplayerSettings.multiSettings.delayStarting)
        {
            PhotonNetwork.CurrentRoom.IsOpen = false;

        }       
        PhotonNetwork.LoadLevel(MultiplayerSettings.multiSettings.multiScene);        
    }

    /// <summary>
    /// Set the parameters for duoMode.
    /// </summary>
    void setToDuoMode()
    {
        Spawner.isDuoMode = true;
        PlayerMovement.isDuoMode = true;
        FireController.isDuoMode = true;
        Health.isDuoMode = true;
        SceneChanger.isDuoMode = true;
        NextLevelDoor.isDuoMode = true;
    }
    
    /// <summary>
    /// Restart the timer.
    /// </summary>
    void RestartTime()
    {
        timeToStart = startTime;
        atMaxPlayers = startTime;
        readyToCount = false;
        isItStart = false;
        timerHost.SetText("");
        timerPlayer.SetText("");
    }
}
