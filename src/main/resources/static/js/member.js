'use strict';
/*
 * CSRF Token
 */
function getCsrfToken() {
    const meta =
        document.querySelector('meta[name="_csrf"]');
    return meta ? meta.content : null;
}
/*
 * CSRF Header
 */
function getCsrfHeader() {
    const meta =
        document.querySelector('meta[name="_csrf_header"]');
    return meta ? meta.content : null;
}
/*
 * 공통 CSRF Header 생성
 */
function getCsrfHeaders() {
    const token = getCsrfToken();
    const header = getCsrfHeader();
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded'
    };
    if (token && header) {
        headers[header] = token;
    }
    return headers;
}
/*
 * 회원 삭제
 */
async function deleteMember(button) {
    const memberId =
        button.dataset.memberId;
    if (!memberId) {
        alert('회원 ID를 확인할 수 없습니다.');
        return;
    }
    const confirmed =
        confirm(
            `회원 ID ${memberId}번을 삭제하시겠습니까?`
        );
    if (!confirmed) {
        return;
    }
    try {
        const response =
            await fetch(
                `/members/del/${memberId}`,
                {
                    method: 'DELETE',
                    headers: getCsrfHeaders()
                }
            );
        if (!response.ok) {
            throw new Error(
                `HTTP ${response.status}`
            );
        }
        const result =
            await response.text();
        console.log(result);
        alert('회원이 삭제되었습니다.');
        window.location.href =
            '/members';
    } catch (error) {
        console.error(error);
        alert(
            '회원 삭제 중 오류가 발생했습니다.'
        );
    }
}
/*
 * 회원 수정
 */
document.addEventListener(
    'DOMContentLoaded',
    () => {
        const form =
            document.getElementById(
                'memberEditForm'
            );
        if (!form) {
            return;
        }
        form.addEventListener(
            'submit',
            async (event) => {
                event.preventDefault();
                const memberId =
                    form.dataset.memberId;
                if (!memberId) {
                    alert(
                        '회원 ID를 확인할 수 없습니다.'
                    );
                    return;
                }
                const formData =
                    new FormData(form);
                const params =
                    new URLSearchParams();
                for (const [key, value]
                    of formData.entries()) {
                    params.append(
                        key,
                        value
                    );
                }
                try {
                    const token =
                        getCsrfToken();
                    const header =
                        getCsrfHeader();
                    const headers = {
                        'Content-Type':
                            'application/x-www-form-urlencoded'
                    };
                    if (token && header) {
                        headers[header] =
                            token;
                    }
                    const response =
                        await fetch(
                            `/members/edit/${memberId}`,
                            {
                                method: 'PUT',
                                headers: headers,
                                body: params.toString()
                            }
                        );
                    if (!response.ok) {
                        throw new Error(
                            `HTTP ${response.status}`
                        );
                    }
                    /*
                     * Controller의
                     *
                     * return "redirect:/members";
                     *
                     * 에 따라 fetch의 response.url을
                     * 사용할 수도 있지만,
                     * 명시적으로 목록으로 이동한다.
                     */
                    alert(
                        '회원 정보가 수정되었습니다.'
                    );
                    window.location.href =
                        '/members';
                } catch (error) {
                    console.error(error);
                    alert(
                        '회원 정보 수정 중 오류가 발생했습니다.'
                    );
                }
            }
        );
    }
);