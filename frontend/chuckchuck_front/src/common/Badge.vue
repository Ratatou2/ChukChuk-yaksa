<!-- 사용법
    <Badge
      title="뱃지 내용"
      backgroundColor="뱃지 색"
      color="글씨 색"
      fontSize="글씨 크기"
      padding="내부 여백"
    /> -->

<template>
  <div class="badge-container" :style="{ backgroundColor, padding }">
    <span class="badge-text" :style="{ color, fontSize }">{{ title }}</span>
  </div>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    default:
      "title : 뱃지 내용, backgroundColor : 뱃지 색, color : 글씨 색, size : 글씨 크기",
  },
  backgroundColor: {
    type: String,
    default: "#34c759",
  },
  color: {
    type: String,
    default: "white",
  },
  fontSize: {
    type: String,
    default: "10px",
  },
  padding: {
    type: String,
    default: "0px",
  },
});
</script>

<style scoped>
.badge-container {
  border-radius: 5px; /* 둥근 모서리 */
  display: inline-flex; /* 필요한 만큼의 공간만 차지 */
}

.badge-text {
  white-space: nowrap; /* 텍스트가 한 줄로 나타나도록 설정 */
  caret-color: transparent;
  font-weight: 600;
  margin: 3.5px 5px;
}
</style>
