using Photon.Pun;
using Photon.Realtime;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

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

    public int playerHealth1;
    public int playerHealth2;

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

    public override void OnEnable()
    {
        base.OnEnable();
        PhotonNetwork.AddCallbackTarget(this);
        SceneManager.sceneLoaded += OnSceneFinishedLoad;

    }

    public override void OnDisable()
    {
        base.OnDisable();
        PhotonNetwork.RemoveCallbackTarget(this);
        SceneManager.sceneLoaded -= OnSceneFinishedLoad;
    }

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
    [PunRPC]
    private void RPC_CreatePlayer()
    {
        PhotonNetwork.Instantiate(Path.Combine("ForMulti","PhotonNetworkPlayer"), transform.position, Quaternion.identity,0);
        playersInGame = playersInGame - 2;
    }

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

    // Update is called once per frame
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
    void setToDuoMode()
    {
        Spawner.isDuoMode = true;
        PlayerMovement.isDuoMode = true;
        FireController.isDuoMode = true;
        Health.isDuoMode = true;
        SceneChanger.isDuoMode = true;
        NextLevelDoor.isDuoMode = true;
    }
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
