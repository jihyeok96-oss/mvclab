document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".js-delete-member").forEach((button) => {
        button.addEventListener("click", async () => {
            const id = button.dataset.memberId;
            const name = button.dataset.memberName;

            if (!confirm(`${name} 회원을 정말 삭제하시겠습니까?`)) {
                return;
            }

            try {
                const response = await fetch(`/members/del/${id}`, {
                    method: "DELETE",
                    headers: {
                        "X-Requested-With": "XMLHttpRequest"
                    }
                });

                if (!response.ok) {
                    throw new Error("회원 삭제에 실패했습니다.");
                }

                alert("회원이 삭제되었습니다.");
                window.location.href = "/members";
            } catch (error) {
                alert(error.message);
            }
        });
    });
});
