using Photon.Pun;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Script for handling player movement
/// </summary>
public class PlayerMovement : MonoBehaviour
{
    [SerializeField]
    private float moveSpeed = 5f;

    private Rigidbody2D rb;
    private Animator animator;

    Vector2 movement;
    Vector2 mousePos;

    public static bool isDuoMode = false;
    private PhotonView pV;
    /// <summary>
    /// Gets the relevant components
    /// </summary>
    void Start()
    {
        moveSpeed = PlayerPrefs.GetFloat("speed", moveSpeed);
        rb = gameObject.GetComponent<Rigidbody2D>();
        animator = gameObject.GetComponent<Animator>();
        if(isDuoMode)
        {
            pV = GetComponent<PhotonView>();
        }
    }

    /// <summary>
    /// Gets player movement inputs
    /// </summary>
    void Update()
    {
        if(isDuoMode && pV.IsMine)
        {
            movement.x = Input.GetAxisRaw("Horizontal");
            movement.y = Input.GetAxisRaw("Vertical");

            animator.SetFloat("Horizontal", movement.x);
            animator.SetFloat("Vertical", movement.y);
            animator.SetFloat("Speed", movement.sqrMagnitude);
        }
        else if(!isDuoMode)
        {
            movement.x = Input.GetAxisRaw("Horizontal");
            movement.y = Input.GetAxisRaw("Vertical");

            animator.SetFloat("Horizontal", movement.x);
            animator.SetFloat("Vertical", movement.y);
            animator.SetFloat("Speed", movement.sqrMagnitude);
        }       
    }

    /// <summary>
    /// Moves the player
    /// </summary>
    void FixedUpdate()
    {
        if(isDuoMode && pV.IsMine)
        {
            rb.MovePosition(rb.position + movement * moveSpeed * Time.fixedDeltaTime);
        }
        else if(!isDuoMode)
        {
            rb.MovePosition(rb.position + movement * moveSpeed * Time.fixedDeltaTime);
        }
        
    }





}

