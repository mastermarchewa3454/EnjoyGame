using Photon.Pun;
using Photon.Realtime;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PhotonRoom : MonoBehaviourPunCallbacks, IInRoomCallbacks
{
    // the room
    public static PhotonRoom theRoom;
    private PhotonView pV;
    public bool isGameLoad;
    public int currentScene;

    // info about players
    Player[] photonPlayers;
    public int playersInRoom;

    Spawner spawner;
    // start the time

    private bool readyToCount;
    private bool isItStart;
    private float atMaxPlayers;
    public float startTime;
    public float timeToStart;
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
        currentScene = scene.buildIndex;
        if(currentScene == MultiplayerSettings.multiSettings.multiScene)
        {
            isGameLoad = true;
            if(MultiplayerSettings.multiSettings.delayStarting)
            {
                pV.RPC("RPC_LoadedGameScene", RpcTarget.MasterClient);
            }
        }
    }

    [PunRPC]
    private void RPC_LoadedGameScene()
    {
        playersInRoom++;
        if(playersInRoom == PhotonNetwork.PlayerList.Length)
        {
            spawner = FindObjectOfType<Spawner>();
            spawner.setDuoMode();
        }
    }


    void Start()
    {
        pV = GetComponent<PhotonView>();
        readyToCount = false;
        isItStart = false;
        timeToStart = startTime;
        atMaxPlayers = 5;
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
                if(isItStart)
                {
                    atMaxPlayers -= Time.deltaTime;
                    timeToStart = atMaxPlayers;
                }
                Debug.Log("Display time to start to the players: " + timeToStart);
                if(timeToStart <= 0)
                {
                    StartGame();
                }
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
            if (playersInRoom == 2)
            {
                isItStart = true;
                if (!PhotonNetwork.IsMasterClient)
                {
                    return;
                }
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
            if (playersInRoom == 2)
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

    void RestartTime()
    {
        timeToStart = startTime;
        atMaxPlayers = 5;
        readyToCount = false;
        isItStart = false;
    }
}
