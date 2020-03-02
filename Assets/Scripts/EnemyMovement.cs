using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class EnemyMovement : NetworkBehaviour
{
    public float speed = 1;
    public float movementInterval = 1;
    public float stopInterval = 2;

    public Rigidbody2D rb;

    Animator anim;
    Vector2 movement;
    AutoFire autoFire;
    bool moving = true;
    bool attacking = false;
    bool faceRight = false;

    void Start()
    {
        anim = GameObject.Find("Sprite").GetComponent<Animator>();
        autoFire = GetComponent<AutoFire>();
        StartCoroutine(NewHeading());
    }

    void FixedUpdate()
    {
        // Move sprite
        rb.MovePosition(rb.position + movement * speed * Time.fixedDeltaTime);
    }

    IEnumerator NewHeading()
    {
        while (this != null)
        {
            // Alternates between idle, movement, idle, attack phase

            anim.SetBool("isMoving", moving);
            anim.SetBool("isAttacking", attacking);

            if (moving)
            {
                // Sets a new direction to head in
                NewHeadingRoutine();
                moving = false;
                yield return new WaitForSeconds(movementInterval);
            }
            else
            {
                // Stops moving
                StopHeading();

                if (attacking)
                {
                    autoFire.Shoot();
                    moving = true;
                }
                attacking = !attacking;
                yield return new WaitForSeconds(stopInterval / 2);
            }
        }
    }

    void StopHeading()
    {
        movement.x = 0;
        movement.y = 0;
    }

    void NewHeadingRoutine()
    {
        movement.x = Random.Range(-1f, 1f);
        movement.y = Random.Range(-1f, 1f);

        // Flip sprite if moving in opposite direction
        if ((movement.x > 0 && !faceRight) || (movement.x < 0 && faceRight))
        {
            rb.transform.localScale = new Vector2(rb.transform.localScale.x * -1, 1);
            faceRight = !faceRight;
        }
    }
}
